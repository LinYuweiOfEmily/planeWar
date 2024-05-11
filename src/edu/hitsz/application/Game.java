package edu.hitsz.application;

import edu.hitsz.aircraft.enemy.*;
import edu.hitsz.aircraft.hero.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.constant.VideoPathConstant;
import edu.hitsz.dao.RankDao;
import edu.hitsz.dao.impl.RankDaoImpl;
import edu.hitsz.factory.enemy.*;
import edu.hitsz.prop.*;
import edu.hitsz.swing.ScoreTable;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    /**
     * 敌机工厂
     */
    private EnemyFactory enemyFactory;

    private int backGroundTop = 0;


    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractEnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;
    private int bossNum = 0;


    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    private int score = 0;

    public int getScore() {
        return score;
    }

    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private int lastScore = 0;
    private long shootStrategy = 0;


    /**
     * 各个音乐线程路径
     */
    private MusicThread bgmThread = new MusicThread(VideoPathConstant.SEE_YOU_AGAIN_PATH);
    private MusicThread bgmBossThread ;
//    private MusicThread bombExplosionThread = new MusicThread("src/videos/bomb_explosion.wav");
//    private MusicThread bulletThread = new MusicThread("src/videos/bullet.wav");
//    private MusicThread bulletHitThread = new MusicThread("src/videos/bullet_hit.wav");
    private MusicThread gameOverThread = new MusicThread(VideoPathConstant.GAME_OVER_PATH);
//    private MusicThread getSupplyThread = new MusicThread("src/videos/get_supply.wav");

    private RankDao rankDao = new RankDaoImpl();


    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    public Game() {
        heroAircraft = HeroAircraft.getInstance();
        props = new LinkedList<>();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        bgmThread.start();
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;


            // 周期性执行（控制频率）

            if(score-lastScore>=1000&&bossNum<1){
                bgmBossThread = new MusicThread(VideoPathConstant.BGM_BOSS_PATH);
                bgmBossThread.start();
                enemyFactory = new BossFactory();
                System.out.println(score+","+lastScore);
                enemyAircrafts.add(enemyFactory.createEnemyAircraft());
                bossNum++;
            }

            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生

                if (enemyAircrafts.size() < enemyMaxNumber) {
                    /**
                     * 随机产生普通敌机或精英敌机或超级精英敌机
                     */
                    double randomNumber = Math.random();
                    if (randomNumber < 0.8) {
                        enemyFactory = new MobFactory();
                    } else if(randomNumber<0.95){
                        enemyFactory = new EliteFactory();
                    } else {
                        enemyFactory = new ElitePlusFactory();
                    }
                    enemyAircrafts.add(enemyFactory.createEnemyAircraft());
                }
                // 飞机射出子弹
                shootAction();
            }

            //道具移动
            propsMoveAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
//                InputName dialog = new InputName();
//                dialog.pack();
//                dialog.setLocationRelativeTo(Main.cardPanel);
//                dialog.setVisible(true);
                ScoreTable scoreTable = new ScoreTable();
                Main.cardPanel.add(scoreTable.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
                scoreTable.inputName();


                bgmThread.stopMusic();
                gameOverThread.start();
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                heroAircraft.increaseHp(500);
            }

        };
        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        //  敌机射击
        for (AbstractEnemyAircraft a : enemyAircrafts) {
            if(a instanceof BossAircraft){
                if(time%1200==0){
                    enemyBullets.addAll(a.shoot());
                }
            }else{
                enemyBullets.addAll(a.shoot());
            }
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
        new MusicThread(VideoPathConstant.BULLET_PATH).start();
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void propsMoveAction() {
        for (BaseProp prop : props) {
            prop.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
//         TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
//         英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    new MusicThread(VideoPathConstant.BULLET_HIT_PATH).start();
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft instanceof BossAircraft){
                            if(bossNum==1){
                                bossNum = 0;
                                bgmBossThread.stopMusic();
                                lastScore = score;
                                score += enemyAircraft.getScore();
                                props.addAll(enemyAircraft.generateNewProp());
                            }
                        }else{
                            score += enemyAircraft.getScore();
                            props.addAll(enemyAircraft.generateNewProp());
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        ListIterator<BaseProp> iterator = props.listIterator();
        while (iterator.hasNext()) {
            BaseProp prop = iterator.next();
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                new MusicThread(VideoPathConstant.GET_SUPPLY_PATH).start();
                // 英雄机撞击到道具
                if (prop instanceof BloodProp bloodProp) {
                    bloodProp.addBlood(heroAircraft);
                } else if (prop instanceof BombProp bombProp) {
                    new MusicThread(VideoPathConstant.BOMB_EXPLOSION_PATH).start();
                    int[] scores = bombProp.bomb(enemyAircrafts, iterator);
                    if(scores[1]==1){
                        bossNum = 0;
                        bgmBossThread.stopMusic();
                        lastScore = score;
                    }
                    score += scores[0];
                } else if (prop instanceof BulletProp bulletProp) {
                    Runnable task = ()->{
                        if("scannerThread".equals(Thread.currentThread().getName())){
                            shootStrategy = Thread.currentThread().threadId();
                            System.out.println(Thread.currentThread().getName());
                            bulletProp.scatterShoot(heroAircraft);
                            try {
                                Thread.sleep(3000);
                                if(shootStrategy==Thread.currentThread().threadId()){
                                    bulletProp.directShoot(heroAircraft);
                                    shootStrategy = 0;
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                    new Thread(task,"scannerThread").start();
                }else if (prop instanceof BulletPlusProp bulletPlusProp){
                    Runnable task = ()->{
                        if("ringThread".equals(Thread.currentThread().getName())){
                            shootStrategy = Thread.currentThread().threadId();
                            System.out.println(Thread.currentThread().getName());
                            bulletPlusProp.ringShoot(heroAircraft);
                            try {
                                Thread.sleep(3000);
                                if(shootStrategy==Thread.currentThread().threadId()){
                                    bulletPlusProp.directShoot(heroAircraft);
                                    shootStrategy = 0;
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                    new Thread(task,"ringThread").start();
                }
                prop.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}

package com.example.aircraftwar2024.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.GameActivity;
import com.example.aircraftwar2024.aircraft.AbstractAircraft;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024.aircraft.BossEnemy;
import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.basic.AbstractFlyingObject;
import com.example.aircraftwar2024.bullet.AbstractBullet;
import com.example.aircraftwar2024.factory.enemy_factory.BossFactory;
import com.example.aircraftwar2024.factory.enemy_factory.EliteFactory;
import com.example.aircraftwar2024.factory.enemy_factory.EnemyFactory;
import com.example.aircraftwar2024.factory.enemy_factory.MobFactory;
import com.example.aircraftwar2024.supply.AbstractFlyingSupply;
import com.example.aircraftwar2024.supply.BombSupply;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 游戏逻辑抽象基类，遵循模板模式，action() 为模板方法
 * 包括：游戏主面板绘制逻辑，游戏执行逻辑。
 * 子类需实现抽象方法，实现相应逻辑
 * @author hitsz
 */
public abstract class BaseGame extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    public static final String TAG = "BaseGame";
    boolean mbLoop; //控制绘画线程的标志位
    private final SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private final Paint mPaint;

    //点击屏幕位置
    float clickX = 0, clickY=0;

    private int backGroundTop = 0;

    /**
     * 背景图片缓存，可随难度改变
     */
    protected Bitmap backGround;

    /**
     * 周期（ms)
     * 控制英雄机射击周期，默认值设为简单模式
     */
    protected double heroShootCycle = 10;
    private int heroShootCounter = 0;

    protected int tickCycle = Integer.MAX_VALUE;
    protected int tickCounter = 0;

    /**
     * 周期（ms)
     * 控制敌机射击周期，默认值设为简单模式
     */
    protected double enemyShootCycle = 20;
    private int enemyShootCounter = 0;

    /**
     * 游戏难度，敌机血量和速度的提升倍率，
     * 普通和困难模式随着时间增加而提高难度，提升普通和精英敌机的速度和血量
     */
    protected double gameLevel = 1.0;


    /**
     * 普通和困难模式中
     * 当得分每超过一次bossScoreThreshold，则产生一次boss机
     */
    protected int bossScoreThreshold = Integer.MAX_VALUE;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 16;

    private final HeroAircraft heroAircraft;

    protected final List<AbstractEnemyAircraft> enemyAircrafts;
    private final List<AbstractFlyingSupply> flyingSupplies;

    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;

    protected int enemyMaxNumber = 5;

    private boolean gameOverFlag = false;
    private int score = 0;


    /**
     * 周期（ms)
     * 控制英雄机射击周期，默认值设为简单模式
     */
    private final int cycleDuration = 60;
    private int cycleTime = 0;
    protected double bossLevel = 1.0;

    protected boolean misDrawing = false;

    /**
     * 产生小敌机和精英敌机。
     * 返回产生的敌机，可以为空链表（即表示不产生）；不能返回null！
     */
    private int enemyCounter = 0;
    protected double enemyCycle = 20;
    /**
     * 控制精英机产生概率，默认值设为简单模式
     */
    protected double eliteProb = 0.3;
    /**
     * 敌机工厂
     */
    private final EnemyFactory mobEnemyFactory;
    private final EnemyFactory eliteEnemyFactory;
    private final EnemyFactory bossEnemyFactory;
    private final Random random = new Random();

    public BaseGame(Context context){
        super(context);

        mbLoop = true;
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
        ImageManager.initImage(context);

        // 初始化英雄机
        heroAircraft = HeroAircraft.getHeroAircraft();
        heroAircraft.setHp(1000);

        enemyAircrafts = new CopyOnWriteArrayList<>();
        heroBullets = new CopyOnWriteArrayList<>();
        enemyBullets = new CopyOnWriteArrayList<>();
        flyingSupplies = new CopyOnWriteArrayList<>();

        mobEnemyFactory = new MobFactory();
        eliteEnemyFactory = new EliteFactory();
        bossEnemyFactory = new BossFactory();

        heroController();
    }
    private void heroShootAction() {
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }
    private void enemyShootAction() {
        // 敌机射击
        for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }
    }
    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        //new Thread(new Runnable() {
        Runnable task = () -> {

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                // produceBoss 根据游戏难度策略产生 BOSS
                //根据游戏设定，随着时间提高游戏难度
                tick();
                enemyAircrafts.addAll(produceBoss());

                // produceEnemy 根据游戏难度策略产生精英敌机和小敌机
                enemyAircrafts.addAll(produceEnemy());
                if(shouldHeroShoot()){
                    heroShootAction();
                }

                if(shouldEnemyShoot()){
                    enemyShootAction();
                }

            }

            // 子弹移动
            bulletsMoveAction();
            // 飞机移动
            aircraftsMoveAction();

            suppliesMoveAction();

            // 撞击检测
            try {

                crashCheckAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 后处理
            postProcessAction();
        };
        task.run();
    }
    /**
     * 每个时刻均调用一次。
     * 普通和困难模式随着时间增加会提高游戏难度
     */
    protected abstract void tick();
    private void suppliesMoveAction() {
        for (AbstractFlyingSupply flyingSupply : flyingSupplies) {
            flyingSupply.forward();
        }
    }
    protected List<AbstractEnemyAircraft> produceBoss() {
        List<AbstractEnemyAircraft> res = new LinkedList<>();

        //当得分每超过一次bossScoreThreshold，且当前无boos机存在，则产生一次boss机
        // 普通模式boss机的血量不会变化
        if (this.getScore() >= bossScoreThreshold && !this.existBoss()) {
            bossScoreThreshold += bossScoreThreshold;
            res.add(bossEnemyFactory.createEnemyAircraft(bossLevel));
        }
        return res;
    }
    public int getScore() {
        return score;
    }
    private boolean existBoss() {
        for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft instanceof BossEnemy) {
                return true;
            }
        }
        return false;
    }


    private List<AbstractEnemyAircraft> produceEnemy() {
        enemyCounter++;
        if (enemyCounter >= enemyCycle) {
            // 每当计数次数达到周期，重置计数器，执行相关指令
            // 后射击周期同理
            enemyCounter = 0;
            List<AbstractEnemyAircraft> res = new LinkedList<>();

            // 产生敌机
            if (enemyAircrafts.size() < enemyMaxNumber) {
                if (random.nextDouble() < eliteProb) {
                    //精英机
                    res.add(eliteEnemyFactory.createEnemyAircraft(gameLevel));
                }
                else{
                    //普通敌机
                    res.add(mobEnemyFactory.createEnemyAircraft(gameLevel));
                }

            }
            return res;
        }
        return new LinkedList<>();
    }

    private boolean shouldHeroShoot() {
        heroShootCounter++;
        if (heroShootCounter >= heroShootCycle) {
            heroShootCounter = 0;
            return true;
        }
        return false;
    }

    private boolean shouldEnemyShoot() {
        enemyShootCounter++;
        if (enemyShootCounter >= enemyShootCycle) {
            enemyShootCounter = 0;
            return true;
        }
        return false;
    }

    public void heroController(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickX = motionEvent.getX();
                clickY = motionEvent.getY();
                heroAircraft.setLocation(clickX, clickY);

                if ( clickX<0 || clickX> GameActivity.screenWidth || clickY<0 || clickY>GameActivity.screenHeight){
                    // 防止超出边界
                    return false;
                }
                return true;
            }
        });
    }


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

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() throws InterruptedException {
        // 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
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
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        //获得分数，产生道具补给
                        score += enemyAircraft.score();
                        flyingSupplies.addAll(enemyAircraft.generateSupplies());
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得补给
        for (AbstractFlyingSupply flyingSupply : flyingSupplies) {
            if (flyingSupply.notValid()) {
                continue;
            }
            if (heroAircraft.crash(flyingSupply) || flyingSupply.crash(heroAircraft)) {
                flyingSupply.activate();
                flyingSupply.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        flyingSupplies.removeIf(AbstractFlyingObject::notValid);

        if (heroAircraft.notValid()) {
            surfaceDestroyed(mSurfaceHolder);
            gameOverFlag = true;
            mbLoop = false;
            Log.i(TAG, "heroAircraft is not Valid");
        }

    }

    public void draw() {
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }

        //绘制背景，图片滚动
        canvas.drawBitmap(backGround,0,this.backGroundTop-backGround.getHeight(),mPaint);
        canvas.drawBitmap(backGround,0,this.backGroundTop,mPaint);
        backGroundTop +=1;
        if (backGroundTop == GameActivity.screenHeight)
            this.backGroundTop = 0;

        //先绘制子弹，后绘制飞机
        paintImageWithPositionRevised(enemyBullets); //敌机子弹


        paintImageWithPositionRevised(heroBullets);  //英雄机子弹


        paintImageWithPositionRevised(enemyAircrafts);//敌机

        paintImageWithPositionRevised(flyingSupplies);//道具


        canvas.drawBitmap(ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY()- ImageManager.HERO_IMAGE.getHeight() / 2,
                mPaint);

        //画生命值和分数
        paintScoreAndLife();

        mSurfaceHolder.unlockCanvasAndPost(canvas);

    }

    private void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, mPaint);
        }
    }

    private void paintScoreAndLife() {
        /**TODO:动态绘制文本框显示英雄机的分数和生命值**/
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(60);
        canvas.drawText("SCORE: "+score,10,40,mPaint);
        canvas.drawText("LIFE: "+heroAircraft.getHp(),10,90,mPaint);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        /*TODO*/
        misDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        GameActivity.screenWidth = i1;
        GameActivity.screenHeight = i2;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        /*TODO*/
        misDrawing = false;
    }

    @Override
    public void run() {
        /*TODO*/
        while (misDrawing){
            action();
            draw();
        }
    }
}

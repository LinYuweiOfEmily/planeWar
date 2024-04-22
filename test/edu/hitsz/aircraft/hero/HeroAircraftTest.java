package edu.hitsz.aircraft.hero;

import edu.hitsz.aircraft.enemy.AbstractEnemyAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.enemy.BossFactory;
import edu.hitsz.factory.enemy.EnemyFactory;
import edu.hitsz.factory.enemy.MobFactory;
import edu.hitsz.factory.prop.BombPropFactory;
import edu.hitsz.factory.prop.PropFactory;
import edu.hitsz.prop.BaseProp;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    HeroAircraft heroAircraft;
    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        heroAircraft = HeroAircraft.getInstance();
    }
    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        heroAircraft = null;
    }

    @DisplayName("Test decreaseHp")
    @Test
    void decreaseHp() {
        heroAircraft.decreaseHp(5);
        assertEquals(495,heroAircraft.getHp());
        heroAircraft.decreaseHp(600);
        assertEquals(0,heroAircraft.getHp());
        assertTrue(heroAircraft.notValid());
    }

    @DisplayName("Test crash(普通敌机)")
    @Test
    void crashEnemy() {
        heroAircraft.setLocation(0,0);
        EnemyFactory enemyFactory = new MobFactory();
        AbstractEnemyAircraft enemyAircraft = enemyFactory.createEnemyAircraft();
        enemyAircraft.setLocation(200,200);
        assertFalse(heroAircraft.crash(enemyAircraft));
        enemyAircraft.setLocation(0,0);
        assertTrue(heroAircraft.crash(enemyAircraft));
    }

    @DisplayName("Test crash(加血道具)")
    @Test
    void crashProp() {
        heroAircraft.setLocation(0,0);
        PropFactory propFactory = new BombPropFactory();
        BaseProp prop = propFactory.createProp(200, 200);
        assertFalse(heroAircraft.crash(prop));
        prop.setLocation(0,0);
        assertTrue(heroAircraft.crash(prop));
    }


    @DisplayName("Test shoot(shootNum==1)")
    @Test
    void shoot() {
        heroAircraft.setLocation(50,50);


        List<BaseBullet> bullets = heroAircraft.shoot();

        //检查生成的子弹数目是否符合预期
        assertEquals(1, bullets.size());

        // 检查子弹的位置是否正确
        assertEquals(50, bullets.get(0).getLocationX()); // 子弹位置
        assertEquals(48, bullets.get(0).getLocationY());

        // 检查子弹的速度是否正确
        assertEquals(0, bullets.get(0).getSpeedX()); // 子弹速度
        assertEquals(-5, bullets.get(0).getSpeedY());

        // 检查每个子弹的威力是否正确
        assertEquals(30, bullets.get(0).getPower());
    }
    @AfterAll
    static void afterAll() {
        System.out.println("**--- Executed once after all test methods in this class ---**");
    }
}
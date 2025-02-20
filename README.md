﻿# 1. **实验环境**

1\.Windows 11

2\.IntelliJ IDEA 2023.3.6

3\.OpenJDK 20

# 2. **实验过程**

## **2.1类的继承关系**

*请根据面向对象设计原则，分析和设计游戏中的所有飞机类、道具类和子弹类，并使用 PlantUML 插件绘制相应的 UML 类图及继承关系，类图中需包括英雄机、所有敌机、道具、子弹及它们所继承的父类。*

![1735558229519](1735558229519.png)

## **2.2设计模式应用**

### **2.2.1单例模式**

1. 应用场景分析

   *描述飞机大战游戏中哪个应用场景需要用到此模式，设计中遇到的实际问题，使用该模式解决此问题的优势。*

   ·**应用场景**：由于在飞机大战中只有一种英雄机，且每局游戏只有一架英雄机，因此对于英雄机而言使用单例模式是更好的

   ·**存在的问题**：

   1. 目前英雄机是在game里面创建的，违反单一职责原则

   `	`2. 任何一个外部程序都可以用new方法创建一个英雄机实例，并不能保证英雄机的唯一性

   ·**使用该模式的优势**：

   `	`1. 对唯一实例的受控访问

   `	`2. 在内存中只有一个实例，减少了内存的开销，避免频繁地创建销毁对象，提高性能

   `	`3. 避免对共享资源的多重占用

   `	`4. 允许可变数目的实例，可以全局访问
2. 设计模式结构图

   *结合飞机大战实例，绘制该场景下具体的解决方案（UML类图）。描述你设计的UML类图结构中每个角色的作用，并指出它的关键属性和方法。*
   ![1735558390855]( 1735558390855.png)

   **·关键属性：**

   instance: 静态属性，用于保存唯一的英雄飞机实例，采用饿汉式单例模式。

   **·关键方法**：

   getInstance: 静态方法，用于获取英雄飞机的唯一实例。

   **·作用：**

   声明了一个名为getInstance获取实例的静态方法来返回其所属类的一个相同实例。这个类实现了英雄飞机对象的基本功能，包括移动、射击、增加生命值等。它是游戏中玩家操控的主要角色，通过键盘或者鼠标控制飞机移动和射击，与游戏中的其他敌人或障碍物进行交互。

### **2.2.2工厂模式**

1. 应用场景分析

   *描述飞机大战游戏中哪个应用场景需要用到此模式，设计中遇到的实际问题，使用该模式解决此问题的优势。*

   **·应用场景**：游戏中有三种类型的敌机和三种类型的道具,因此这两种情况下用工厂模式会很好

   **·存在的问题**：

   1. 目前敌机和道具都是在game里面创建，违反单一职责原则

   `	`2. 若增加其他新型机或道具时，需要修改原有的模块，违反开闭原则

   `	`3. 针对实现编程，而不是针对接口编程，违反了依赖倒转原理

   **·使用该模式的优势**：

   `	`1. 封装对象的创建过程，降低耦合。提高系统的可维护性和可扩展性1

   `	`2. 统一管理对象的创建

   `	`3. 符合开闭原则，对扩展开放、对修改关闭

   `	`4. 隐藏复杂逻辑：对客户端隐藏实现细节，可以使客户端代码更加简洁、清晰
2. 设计模式结构图

   *结合飞机大战实例，绘制该场景下具体的解决方案（UML类图）。描述你设计的UML类图结构中每个角色的作用，并指出它的关键属性和方法。*
   ![1735558569740]( 1735558569740.png)

   <center> 图1. PropFactory </center>

![1735558698792]( 1735558698792.png)

<center>**图2. EnemyFactory**</center>

   **图1：**

   ·PropFactoy:接口,其接口方法createProp用于创建敌机类AbstractEnemyAircraft

   `	`·BloodPropFactory:继承接口PropFactory，实现接口方法createProp返回BloodProp类型的道具

   `	`·BombPropFactory：继承接口PropFactory，实现接口方法createProp返回BombProp类型的道具

   `	`·BulletPropFactory：继承接口PropFactory，实现接口方法createProp返回BulletProp类型的道具

   `	`·BulletPlusPropFactory：继承接口PropFactory，实现接口方法createProp返回BulletPlusProp类型的道具

   ·BaseProp：抽象类，继承AbstractAircraft，关键属性score是击杀敌机所获得的分数；抽象方法generateNewProp是击杀敌机后产生道具的方法

   `	`·BloodProp：继承BaseProp，关键属性blood表示吃到加血道具时回的血量，关键方法addBlood()是让英雄机加血

   `	`·BombProp：继承BaseProp，关键属性power是爆炸的伤害值。关键方法bomb对全屏的敌人造成伤害

   `	`·BulletProp：继承BaseProp，关键方法addShootNum使英雄机的子弹数目增加

   **图2：**

   ·EnemyFactoy:接口,其接口方法createEnemyAircraft用于创建敌机类AbstractEnemyAircraft

   `	`·MobFactory:继承接口EnemyFactory，实现接口方法createEnemyAircraft返回MobEnemy类型的敌机

   `	`·EliteFactory：继承接口EnemyFactory，实现接口方法createEnemyAircraft返回EliteAircraft类型的敌机

   `	`·ElitePlusFactory：继承接口EnemyFactory，实现接口方法createEnemyAircraft返回ElitePlus类型的敌机

   `	`·BossFactory：继承接口EnemyFactory，实现接口方法createEnemyAircraft返回Boss类型的敌机

   ·AbstractEnemyAircraft：抽象类，继承AbstractAircraft，关键属性score是击杀敌机所获得的分数；抽象方法generateNewProp是击杀敌机后产生道具的方法;

   `	`·MobEnemy：继承AbstractEnemyAircraft，是普通敌机类

   `	`·EliteAircraft：

   `		`·关键方法：

   `			`shoot(): 射击方法，用于产生直射子弹，并返回射出的子弹列表。

   `			`generateNewProp():击杀该机后随机产生道具或不产生。

   `	`·ElitePlus：

   `		`·关键方法：

   `			`shoot(): 射击方法，用于产生散射子弹，并返回射出的子弹列表。

   `			`generateNewProp():击杀该机后随机产生道具或不产生。

   `	`·BossAircraft：

   `		`·关键方法：

   `			`shoot(): 射击方法，用于产生环射子弹，并返回射出的子弹列表。

   `			`generateNewProp():击杀该机后随机产生道具（最多三个）或不产生。

### **2.2.3策略模式**

1. 应用场景分析

   *描述飞机大战游戏中哪个应用场景需要用到此模式，设计中遇到的实际问题，使用该模式解决此问题的优势。*

   **·应用场景**：飞机大战游戏中，不同类型飞机的发射弹道各不相同，且火力道具生效时英雄机切换弹道，且相同弹道的实现对于英雄机和敌机是一样的，因此应该使用策略模式

   **·存在的问题**：

   1. 要实现英雄机弹道的切换需要把散射和环射代码复制一份到英雄机，重复代码多，代码难以维护，违反了合成复用原则

   2. 如果要给英雄机加弹道需要修改代码，违反开闭原则

   ·**使用该模式的优势**：

   `	`1. 多重条件语句不易维护，使用策略模式可以避免使用多重条件语句

   `	`2. 算法可以自由切换

   `	`3. 扩展性良好

   `	`4. 策略模式提供了一系列的可供重用的算法族，恰当使用继承可以把算法族的公共代码转移到父类里面，从而避免重复的代码
2. 设计模式结构图

   *结合飞机大战实例，绘制该场景下具体的解决方案（UML类图）。描述你设计的UML类图结构中每个角色的作用，并指出它的关键属性和方法。*

   解决方案：

   创建一个ShootStrategy接口，充当抽象策略角色；

   创建三个子弹策略实体类(直射、散射和环射)；

   AbstractAircraft为上下文角色；

   Game类使用AbstractAircraft来实现不同策略
3. *将PlantUML插件绘制的类图截图到此处*
   ![1735559030231]( 1735559030231.png)

   ·ShootStrategy:接口,其接口方法doShoot用于根据策略生成子弹

   ·DirectShoot:继承接口ShootStrategy，实现接口方法doShoot生成直射弹道的子弹

   ·RingShoot：继承接口ShootStrategy，实现接口方法doShoot生成环形弹道的子弹

   ·ScatterShoot：继承接口ShootStrategy，实现接口方法doShoot生成散射弹道的子弹

   ·AbstractAircraft：抽象类，继承AbstractFlyingObject，关键属性shootStrategy是射击的策略；方法setShootStrategy()是选择射击策略的方法，shoot()是根据策略进行射击的方法

   ·Game是实现射击策略的类

   ·BulletPlusProp：英雄机捡到该道具会产生环射的子弹，持续3秒

   ·BulletProp：英雄机捡到该道具会产生散射的子弹，持续3秒

### **2.2.4数据访问对象模式**
1. 应用场景分析

   *描述飞机大战游戏中哪个应用场景需要用到此模式，设计中遇到的实际问题，使用该模式解决此问题的优势。*

   **·应用场景**：每局游戏记录英雄机得分，游戏结束后，显示该难度的玩家得分排行榜。因此需要存储玩家的得分数据，并能显示出排行榜所以需要使用数据访问对象模式

   **·使用该模式的优势**：

   `	`1. 隔离数据层：由于新增了DAO层，不会影响到服务或者实体对象与数据库交互，发生错误会在该层进行异常抛出。

   `	`2. DAO的作用在于提供一种手段，来读取和写入数据库，他们应该通过接口的方式来提供这种功能，让程序的其他部分可以访问他们，而不必与特定的读取和写入数据库的实现方式进行绑定。
2. 设计模式结构图

   *结合飞机大战实例，绘制该场景下具体的解决方案（UML类图）。描述你设计的UML类图结构中每个角色的作用，并指出它的关键属性和方法。。*

   方案：

   创建一个作为数值对象的实体类Rank

   创建数据访问对象DAO接口

   创建实现上述接口的DAO实现类

   使用Game来进行数据访问对象
![1735559122332]( 1735559122332.png)

   ·RankDao:接口,其接口方法select和add

   `	`·RankDaoImpl:继承接口RankDao，实现接口方法select用于排序并查询全部记录和add用于添加新的记录

   ·Rank：关键属性name，score，time分别表示产生此次记录的用户名，分数以及时间，方法是这三个属性所对应的getter和setter方法

   ·Game：将本次产生的记录存储起来并展示排序后的所有数据

### **2.2.5观察者模式**
1. 应用场景分析

   *描述飞机大战游戏中哪个应用场景需要用到此模式，设计中遇到的实际问题，使用该模式解决此问题的优势。*

   **·应用场景**：敌机坠毁时会以较低概率掉落炸弹道具。它可清除界面上的所有的普通、精英敌机和敌机子弹，超级精英敌机血量减少，Boss敌机不受影响。英雄机可获得坠毁的敌机分数，也就是说捡到炸弹之后各部分敌机都要发生不同的变化，适合使用观察者模式

   **·存在的问题**：

   `	`1. 增加一个对炸弹有响应的新的敌机类，需要在炸弹爆炸的代码中，多加一个if-else，违反了单一职责原则。

   `	`2. 如果再增加一个新的对敌机全体有影响的道具类，需要再写一份与炸弹类爆炸逻辑类似的代码，复用性太差。

   **·使用该模式的优势**：

   `	`1. 可以实现表示层和数据逻辑层的分离

   `	`2. 在观察目标和观察者之间建立一个抽象的耦合

   `	`3. 支持广播通信，简化了一对多系统设计的难度

   `	`4. 符合开闭原则，增加新的具体观察者无须修改原有系统代码，在具体观察者

   与观察目标之间不存在关联关系的情况下，增加新的观察目标也很方便
2. 设计模式结构图

   *结合飞机大战实例，绘制该场景下具体的解决方案（UML类图）。描述你设计的UML类图结构中每个角色的作用，并指出它的关键属性和方法。*
![1735559253189]( 1735559253189.png)

   抽象观察者：为所有具体的观察者定义一个接口，在得到目标的通知时更新自己。

   具体目标（BombProp类）：在英雄机捡到炸弹道具后，通知所有登记过的观察者发生改变。

   关键方法为bomb（）、addFlying（）、removeFlying（）、removeAllFlying（）、notifyAllEnemy（）

   具体观察者：该角色实现抽象观察者角色所要求的更新接口，以便使本身的状态与目标的状态相协调，关键方法：update（）

   `	`·MobEnemy和EliteAircraft：直接销毁，并返回分数

   `	`·BossAircraft：不受影响，返回0

   `	`·ElitePlus: 受到50点伤害，如果销毁了返回分数

   `	`·EnemyBullet：直接消失，返回0

### **2.2.6模板模式**
1. 应用场景分析

   *请简单描述你对三种游戏难度是如何设计的，影响游戏难度的因素有哪些。描述飞机大战游戏中哪个应用场景需要用到此模式，设计中遇到的实际问题，使用该模式解决此问题的优势。*

   ·**难度设计：**

   | 游戏难度                             | 简单模式         | 普通模式         | 困难模式       |
   | :----------------------------------- | :--------------- | :--------------- | :------------- |
   | 有无Boss敌机                         | 无               | 有               | 有             |
   | 难度是否随时间增长而增长             | 否               | 是               | 是             |
   | 每次召唤Boss敌机是否血量增长         | 否               | 是               | 是             |
   | 初始难度系数                         | 1                | 1\.25            | 1\.5           |
   | 子弹射击频率                         | 600              | 560              | 520            |
   | 初始普通，精英机、超级精英机概率     | 0\.8，0.15，0.05 | 0\.7，0.25，0.05 | 0\.7，0.2，0.1 |
   | 屏幕上最大敌机数目                   | 5                | 6                | 7              |
   | Boss机是否会有概率产生威力更大的子弹 | 否               | 否               | 是             |

   注：难度随着时间增加有两个部分，一部分是敌机的属性包括血量、分数、伤害等都上调；另一部分是产生普通、精英和超级精英机的概率变化，随着时间推移精英机和超级精英机的产生概率变高

   **·应用场景**：用户进入游戏界面后，可选择某种游戏难度：简单/普通/困难。用户选择后，出现该难度对应的地图，且游戏难度会相应调整，也就是每个模式只需要修改原代码中一些可变的部分，不变的部分可以由抽象父类实现，因此非常适合使用模板模式

   **·存在的问题**：

   `	`我们原来的Game类已经实现了所有的功能，现在需要找到其中可变的部分封装成抽象类留给子类去实现。

   **·使用该模式的优势**：

   `	`1. 模板方法模式是通过把不变的行为挪到一个统一的父类，从而达到去除子类中重复代码的目的

   `  `2. 子类实现模板父类的某些细节，有助于模板父类的扩展

   `  `3. 通过一个父类调用子类实现的操作，通过子类扩展增加新的行为，符合“开放-封闭原则”
2. 设计模式结构图

   *结合飞机大战实例，绘制该场景下具体的解决方案（UML类图）。描述你设计的UML类图结构中每个角色的作用，并指出它的关键属性和方法。*
![1735559394819]( 1735559394819.png)

   抽象模板父类（Game）：实现飞机大战游戏三个难度的不变部分，并会声明作为算法步骤的方法，以及依次调用它们的实际模板方法。算法步骤可以被声明为抽象类型，也可以提供一些默认实现。

   `	`·关键属性：level、cycleDuration、createMobEnemy、createEliteEnemy、enemyMaxNumber

   `	`·关键方法：isGenerateBoss（）、increaseLevel（）、drawBackGround（）

   具体类（CommonGame、EasyGame、HardGame）:重写父类的钩子方法和抽象方法，以及不同的初始化数据。
# 3.**收获和反思**

*请填写本次实验的收获，记录实验过程中出现的值得反思的问题及你的思考。*

*欢迎为本课程实验提出宝贵意见！*

**收获：**

1. **设计模式的理解与应用**：
   1. 通过实现飞机大战项目，我深入理解了设计模式的重要性和实用性。例如，观察者模式用于处理炸弹爆炸逻辑，工厂模式用于创建不同类型的敌机和道具等。
   2. 我学会了如何在Java代码中具体实现这些设计模式，提高了代码的复用性和可维护性。
2. **面向对象编程的深化**：
   1. 在项目中，我使用了许多面向对象的特性，如继承、封装和多态。这使我更加熟悉了Java面向对象编程的精髓。
   2. 通过定义不同的类和接口，我能够清晰地划分代码的职责，提高了代码的可读性和可扩展性。
3. **游戏开发流程的体验**：
   1. 在这个过程中，我学会了如何将复杂的游戏逻辑分解为可管理的模块，并逐个实现。
4. **图形用户界面（GUI）的开发**：
   1. 我学会了使用Java的图形用户界面库（如Swing或JavaFX）来创建游戏的界面。
   2. 我掌握了如何处理用户输入、绘制图形和动画等技能。
5. **多线程编程的初步实践**：
   1. 为了实现游戏的流畅运行，我使用了多线程技术来处理音乐的播放和火力道具的使用。

**值得反思的问题及思考**

1. **性能优化**：
   1. 在实验过程中，我发现游戏的性能在某些情况下不够理想。这可能是由于我的代码实现不够高效，或者没有充分利用Java的性能优化技术。
   2. 思考：我可以尝试使用更高效的数据结构和算法来优化代码性能；同时，我也可以学习一些Java的性能优化技术，如垃圾回收器的调优、JIT编译器的使用等。
2. **代码重构**：
   1. 随着项目的进行，我发现有些代码模块之间的耦合度过高，这不利于代码的维护和扩展。
   2. 思考：我应该在项目初期就注重代码的结构和模块划分，尽量避免出现高耦合度的代码；同时，我也应该学会使用设计模式来降低代码的耦合度，提高代码的可维护性和可扩展性。

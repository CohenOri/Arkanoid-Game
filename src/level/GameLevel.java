package level;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import block.Block;
import collisiondetection.BallRemover;
import collisiondetection.BlockRemover;
import collisiondetection.Counter;
import collisiondetection.GameEnvironment;
import collisiondetection.ScoreTrackingListener;
import gamelogic.Animation;
import gamelogic.AnimationRunner;
import gamelogic.CountdownAnimation;
import gamelogic.KeyPressStoppableAnimation;
import gamelogic.PauseScreen;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import geometryprimitives.Velocity;
import sprites.Ball;
import sprites.Collidable;
import sprites.LevelIndicator;
import sprites.LivesIndicator;
import sprites.Paddle;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;

/**
 * GameLevel Class to define the actual game/level we see on the screen (the
 * blocks size, the paddle location the ball Velocity and so on). game holds the
 * sprites which is basicly everything we see on the screen and "environment"
 * which is the environment where our ball moves, environment includes all the
 * collidable objects.
 * GameLevel is generic, it reads the whole particular level definiton from given
 * LevelInformation variable given upon construction. It also recevies the current
 * ammount of lives & score. and GUI to draw level on.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainedBlocks;
    private Counter remainedBalls;
    private Counter score;
    private Counter numberOfLives;
    private AnimationRunner runner;
    private boolean running;
    private Paddle pad;

    private LevelInformation levelInformation;
    private GUI gui;
    /**
     * constructor using score & lives Counters, LevelInformation object and GUI to draw on.
     * creates GameLevel object.
     * @param currentScore - Counter with currentScore before starting this particular level.
     * @param currentLives - Counter with currentLives before starting this particular level.
     * @param lI - LevelInformation object to define the actual level we to create (look, blocks, balls).
     * @param g - GUI to draw on.
     */
    public GameLevel(Counter currentScore, Counter currentLives, LevelInformation lI, GUI g) {
        this.levelInformation = lI;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainedBlocks = new Counter(this.levelInformation.numberOfBlocksToRemove());
        this.remainedBalls = new Counter(this.levelInformation.numberOfBalls());
        this.gui = g;
        this.score = currentScore;
        this.numberOfLives = currentLives;
    }

    /**
     * Adds Collidable object to the GameEnvironment.
     * @param c - Collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * adds Sprite object to the SpriteCollection.
     * @param s - Sprite object to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new particular level based, gives each level the base features as ScoreIndicator
     * and define the actual level look based on the levelInformation (exp. number of blocks...).
     */
    public void initialize() {
        // define the base look of each level, Score, Lives, Level Name and Background
        ScoreIndicator sIndicator = new ScoreIndicator(this.score);
        sIndicator.addToGameLevel(this);
        LivesIndicator lIndicator = new LivesIndicator(this.numberOfLives);
        lIndicator.addToGameLevel(this);
        LevelIndicator levelIndicator = new LevelIndicator(this.levelInformation.levelName());
        levelIndicator.addToGameLevel(this);
        // add the background to the game
        this.levelInformation.getBackground().addToGameLevel(this);

        // adds the listeners in charge of the dynamic part of the game
        // removing blocks, balls and score tracking
        BlockRemover blocksRemoverListener = new BlockRemover(this, this.remainedBlocks);
        BallRemover ballsRemoverListener = new BallRemover(this, this.remainedBalls);
        ScoreTrackingListener sTrackingListener = new ScoreTrackingListener(this.score);
        // adds the base block borders of the game
        Block border0 = new Block(new Rectangle(new Point(0, 30), 800, 10), 0);
        border0.addToGameLevel(this);
        Block border1 = new Block(new Rectangle(new Point(0, 0), 10, 600), 0);
        border1.addToGameLevel(this);
        Block border2 = new Block(new Rectangle(new Point(790, 0), 10, 600), 0);
        border2.addToGameLevel(this);

        // add "death" region: block that removes ball upon hit & update the remainBalls counter.
        Block deathBorder = new Block(new Rectangle(new Point(0, 600), 800, 10), 0);
        deathBorder.addToGameLevel(this);
        deathBorder.addHitListener(ballsRemoverListener);

        // Make a copy of the blocks before iterating over them.
        List<Block> blocks = new ArrayList<Block>(this.levelInformation.blocks());
        // create all the blocks for the particular level (using the given LevelInformation)
        // then adds them the needed Listeners
        for (Block b : blocks) {
            b.addToGameLevel(this);
            b.addHitListener(blocksRemoverListener);
            b.addHitListener(sTrackingListener);
        }
        // some more initialization - of the AnimationRunner in charge of the running the Animation
        this.runner = new AnimationRunner(this.gui);
    }
    /**
     * Runs the particular level as long the user has lives and still hasn't finished.
     * if failed decrease number of lives.
     */
    public void run() {
        this.playOneTurn();
        if (this.remainedBalls.getValue() <= 0) {
            this.numberOfLives.decrease(1);
        }
        // run the game again while there is still lives & blocks to destroy
        while (this.numberOfLives.getValue() > 0 && this.remainedBlocks.getValue() > 0) {
            // reset the number of balls to the number of balls from the level,
            // caution it also infulence on ballRemover
            // and should look exactly as below
            this.remainedBalls.increase(this.levelInformation.numberOfBalls());
            this.playOneTurn();
            if (this.remainedBalls.getValue() <= 0) {
                this.numberOfLives.decrease(1);
            }
        }
    }

    /**
     * @return the remainedBlocks
     */
    public Counter getRemainedBlocks() {
        return remainedBlocks;
    }

    /**
     * removes the given Collidable object from the GameLevel.
     * @param c Collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidables(c);
    }

    /**
     * removes the given Sprite object from the GameLevel.
     * @param s Sprite object to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
    /**
     * @return if should stop run the level.
     */
    public boolean shouldStop() {
        return !this.running;
    }
    /**
     * runs one frame of the game, if should stop the game updating the "this.running" member.
     * @param d surface to draw on
     * @param dt dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // if there's no more blocks or balls in the game stop running him
        if (this.remainedBlocks.getValue() <= 0 || this.remainedBalls.getValue() <= 0) {
            this.running = false;
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        // if pressed "P" pause the game
        if (this.gui.getKeyboardSensor().isPressed("p")) {
            this.runner.run(new PauseScreen());
            // press Space to continue
            this.runner.run(new KeyPressStoppableAnimation(this.gui.getKeyboardSensor(),
                    KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
    }
    /**
     * create the paddle and start running one cycle of the level (with no failures of player).
     */
    public void playOneTurn() {
        // create Balls & Paddle
        this.createBallsAndPaddle();
        // Create the "3 2 1 GO" screen
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        // define game to run
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
        // adding 100 points to score when all the blocks are removed
        if (this.remainedBlocks.getValue() <= 0) {
            this.score.increase(100);
        }
        // remove the pad from game, in order to create new one next time
        this.pad.removeFromGameLevel(this);
    }
    /**
     * create balls and paddle based on the given levelInformation and add them to the screen.
     */
    public void createBallsAndPaddle() {
        // create balls
        // Make a copy of the BallVelocites before iterating over them.
        List<Velocity> ballVelocities = new ArrayList<Velocity>(this.levelInformation.initialBallVelocities());
        // create all the balls at the top of the paddle, add them the needed
        // Listeners
        for (Velocity v : ballVelocities) {
            Ball b = new Ball(400, 570, this.levelInformation.ballsRadius(), this.levelInformation.ballsColor(), v,
                    this.environment);
            b.addToGameLevel(this);
        }
        // create the paddle at the center of the screen,
        // gives it KeyboardSensor, rectSize speed and surface to drawOn
        int padWidth = this.levelInformation.paddleWidth();
        int padSpeed = this.levelInformation.paddleSpeed();
        this.pad = new Paddle(this.gui.getKeyboardSensor(),
                new Rectangle(new Point((800 - padWidth) / 2, 570), padWidth, 20), this.gui.getDrawSurface(), padSpeed);
        this.pad.addToGameLevel(this);
    }

}
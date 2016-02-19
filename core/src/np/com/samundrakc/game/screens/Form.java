package np.com.samundrakc.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Timer;
import java.util.TimerTask;

import np.com.samundrakc.game.DailaMaara;
import np.com.samundrakc.game.anchors.Const;
import np.com.samundrakc.game.anchors.Game;
import np.com.samundrakc.game.controllers.FormCtrl;
import np.com.samundrakc.game.misc.Animation;
import np.com.samundrakc.game.misc.Context;
import np.com.samundrakc.game.misc.MessageBox;
import np.com.samundrakc.game.misc.Prefs;
import np.com.samundrakc.game.misc.Utils;

/**
 * Created by samundra on 2/2/2016.
 */
public class Form extends ScreenRules {
    public CheckBox getRemberFriendCheckBox() {
        return remberFriendCheckBox;
    }

    private CheckBox remberFriendCheckBox;

    public Prefs getPref() {
        return pref;
    }

    private final Prefs pref;
    TextField name;
    private TextButton back;

    public TextField getGroup() {
        return group;
    }

    public TextField getName() {
        return name;
    }

    TextField group;

    public Table getTable() {
        return table;
    }

    Table table;
    Button play;
    FormCtrl formCtrl;
    DailaMaara dailaMaara;

    @Override
    public void dispose() {
        super.dispose();
    }

    public DailaMaara getDailaMaara() {
        return dailaMaara;
    }

    public Form(DailaMaara game) {
        super(game);
        this.dailaMaara =  game;
        Game mainGame = new Game();
        mainGame.createCards();
        mainGame.shuffleCardsOFGame(Game.cards);
        pref = new Prefs("form");
        formCtrl = new FormCtrl(this, mainGame);
        initWidgets();
        selectPlayerForm();
        selectCardForDistrubutor();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        game.clearView();
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        table.setPosition(Utils.inCenter(table, 'x'), Utils.inCenter(table, 'y'));
        stage.addActor(table);
        stage.addActor(selectPlayerTable);
        if (pref.getString("name", null) != null && pref.getString("group", null) != null) {
            if (pref.getInt("rememberFriend") == 1) {
                formCtrl.playButton().touchDown(null, 0, 0, 0, 0);
                formCtrl.computerCtrl(pref.getInt("friend")).touchDown(null, 0, 0, 0, 0);
                return;
            }
            formCtrl.playButton().touchDown(null, 0, 0, 0, 0);
        }
    }

    private void initWidgets() {
        name = new TextField("", Context.skin);
        group = new TextField("", Context.skin);
        name.setText(pref.getString("name", ""));
        group.setText(pref.getString("group", ""));
        Label nameLabel = new Label("Enter your name",Context.skin);
        Label groupLabel = new Label("Enter your group name", Context.skin);
        play = new TextButton("NEXT", Context.skin);
        play.setBounds(0, 0, play.getWidth(), play.getHeight());
        play.addListener(formCtrl.playButton());
        nameLabel.setFontScale(5);
        table = new Table();
        table.add(nameLabel).padTop(5).row();
        table.add(name).padTop(5).row();
        table.add(groupLabel).padTop(5).row();
        table.add(group).padTop(5).row();
        table.add(play).padTop(5);
        table.center();

        table.setFillParent(!true);
    }

    Button[] computer;
    Table selectPlayerTable;

    public Table getSelectPlayerTable() {
        return selectPlayerTable;
    }

    private void selectPlayerForm() {
        computer = new Button[3];
        selectPlayerTable = new Table();
        Label nameLabel = new Label("Select your friend", Context.skin);
        selectPlayerTable.add(nameLabel).colspan(5).expandX().padBottom(10).row();
        for (int i = 0; i < computer.length; i++) {
            computer[i] = new TextButton("Computer " + (i + 1), Context.skin);
            computer[i].setBounds(0, 0, computer[i].getWidth(), computer[i].getHeight());
            computer[i].addListener(formCtrl.computerCtrl(i));
            selectPlayerTable.add(computer[i]).pad(5).expandX();
        }
        selectPlayerTable.row();
        remberFriendCheckBox = new CheckBox(" Remember My Friend", Context.skin);
        System.out.println(pref.getInt("rememberFriend"));
        remberFriendCheckBox.setChecked(pref.getInt("rememberFriend") == 1);
        remberFriendCheckBox.setBounds(0, 0, remberFriendCheckBox.getWidth(), remberFriendCheckBox.getHeight());
        remberFriendCheckBox.addListener(formCtrl.remeberFriend());
        back = new TextButton("Back", Context.skin);
        back.setBounds(0, 0, back.getWidth(), back.getHeight());
        back.addListener(formCtrl.backButton());
        selectPlayerTable.add(remberFriendCheckBox).colspan(5).padTop(10).row();
        selectPlayerTable.add(back).colspan(5).padTop(10);
        selectPlayerTable.center().setFillParent(true);
        selectPlayerTable.setPosition(Context.WIDTH + selectPlayerTable.getWidth(), 0);
    }

    public Group getStacks() {
        return stacks;
    }

    final Group stacks = new Group();

    public Group getStacksChild() {
        return stacksChild;
    }

    final Group stacksChild = new Group();

    public void selectCardForDistrubutor() {
        final int[] gap = {3};

        stacks.setSize(50, 70);
        stacks.setPosition(gap[0], (50));
        for (int i = 0; i < Const.TOTAL_NUMBER_OF_CARDS; i++) {
            stacks.addActor(new FormCtrl.BackCover(i));
            stacks.getChildren().get(i).setPosition(gap[0], gap[0]);
            stacks.getChildren().get(i).addListener(formCtrl.cardsListener(i, stacks.getChildren().get(i)));
        }
        stacks.setPosition(Context.WIDTH + selectPlayerTable.getWidth() + stacks.getWidth(), 3);
        stacks.setTouchable(Touchable.disabled);
        stage.addActor(stacks);
        stage.addActor(stacksChild);
    }
}

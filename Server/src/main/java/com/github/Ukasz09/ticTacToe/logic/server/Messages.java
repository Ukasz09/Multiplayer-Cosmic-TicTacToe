package com.github.Ukasz09.ticTacToe.logic.server;

public class Messages {
    //From clients
    public static final String SIGN_BTN_CLICKED = "sign_chosen";
    public static final String AVATAR_BTN_CLICKED = "avatar_chosen";
    public static final String CHOSEN_VALID_NAME = "chosen_valid_name";
    public static final String START_BTN_CLICKED = "start_btn";
    public static final String BOARD_SIZE_CHOSEN = "board_size_chosen";
    public static final String BOX_BTN_CLICKED = "game_box_clicked";
    public static final String END_GAME_BTN_CLICKED = "end_btn";
    public static final String REPEAT_GAME_BTN = "repeat_btn";

    //From server
    public static final String DELIMITER = ":";
    public static final String SCENE_TO_NICK = "change_scene_to_nick_choose";
    public static final String SCENE_TO_BOARD = "change_scene_to_board";
    public static final String SCENE_TO_WINNER = "scene_to_winner_page";
    public static final String SCENE_TO_DRAW = "scene_to_draw_page";
    public static final String SCENE_TO_AVATAR = "scene_to_avatar_page";
    public static final String SCENE_TO_SIGN_CHOOSE = "scene_to_sign_page";
    public static final String SCENE_TO_BOARD_SIZE = "scene_to_board_size_page";
    public static final String CLOSE_GUI = "close_main_stage";
    public static final String CLEAR_NODES = "clear_action_nodes";
    public static final String RESET_PLAYER_ID = "reset_actual_player_id";
    public static final String ADD_SIGN_TO_BOX = "add_player_sign_to_box";
    public static final String DENY_INTERACTION_WITH_BOXES = "deny_interaction_with_boxes";
    public static final String CHANGE_BOXES_STATE = "change_boxes_state";
    public static final String WAITING_FOR_OTHER_PLAYER = "waiting_for_other_player";
}

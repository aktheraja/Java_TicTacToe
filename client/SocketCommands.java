package exer5.client;

public interface SocketCommands {
	static final String CHECK_XPLAYER_REGISTERED="*chk_XPlayer*";
	static final String CHECK_OPLAYER_REGISTERED="*chk_OPlayer*";
	static final String RESTART_GAME="*rstrt_gm*";
	static final String START_GAME="*strt_gm*";
	static final String SET_MATCHUP = "*ref_set_the_match*";
	static final String CREATE_PLAYER="*create_players_matchup*";
	static final String CHECK_REG_PLAYERS = "*check_player*";
	static final String CHECK_IF_TO_CONTINUE = "*checkIfToContinue*";
	static final String CHECK_IF_ANY_MOVE = "*checkIfAnyMoveYet*";
	static final String SET_OPP_OF_CURRENT_PLAYER_AS_CURRENT= "*set_current_opp_as_current*";
	static final String GET_MARK_OF_CURRENT_PLAYER = "*get_mark_of_current*";
	static final String GET_NAME_OF_CURRENT_PLAYER = "*get_name_of_current*";
	static final String BROADCAST__GAME_RESULT="*get_result_and_display_to_all_windows*";
	static final String CHECK_WHETHER_CELL_PLAYED = "*chk_if_cell_played*";
	static final String PLAY_CELL="*play_cell*";
	static final String START_NEW_GAME = "*start_new_game*";
	static final String BROADCAST__DISPLAY_PLAY = "*display_play_on_all_windows*";
	static final String BROADCAST__DISPLAY_CURRENT_PLAYER = "*display_current_player_on_all_windows";
	static final String BROADCAST__CLEAR_EVERYTHING = "*restart_game*";
	static final String CHECK_MATCHUP_IS_SET= "*check_matchup_is_done*";
}

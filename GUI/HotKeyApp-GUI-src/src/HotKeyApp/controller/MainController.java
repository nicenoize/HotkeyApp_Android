package HotKeyApp.controller;

import HotKeyApp.Main;
import HotKeyApp.model.*;
import com.esotericsoftware.jsonbeans.Test;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * HotKeyApp_GUI v0.5
 * #############################
 * @author Thorsten Meier, Alexander Stahl
 * @version 0.5
 * #############################
 */

public class MainController implements Initializable{
    //////////////////////////////////
    // CONTROLLER-ATTRIBUTES
    //////////////////////////////////
    private HotkeyMap selectedMap;
    private final HotkeyMap ownMap = new HotkeyMap();
    private final HotkeyServer hotkeyServer = new HotkeyServer();
    private boolean serverStarted = false;
    //// END CONTROLLER-ATTRIBUTES ////

    //////////////////////////////////
    // HOVER IMAGES
    //////////////////////////////////
    // ON
    private final Image hoverOn_off =                   getGraphics("btn_off_hover.png");
    private final Image hoverOn_minimize =              getGraphics("btn_minimize_hover.png");
    private final Image hoverOn_settings_and_general =  getGraphics("btn_settings_hover.png");
    private final Image hoverOn_about =                 getGraphics("btn_about_hover.png");
    private final Image hoverOn_user =                  getGraphics("btn_user_hover.png");
    private final Image hoverOn_server =                getGraphics("btn_logo_hover.png");
    private final Image hoverOn_experimental =          getGraphics("btn_experimental_hover.png");
    // OFF
    private final Image hoverOff_off =                  getGraphics("btn_off.png");
    private final Image hoverOff_minimize =             getGraphics("btn_minimize.png");
    private final Image hoverOff_settings_and_general = getGraphics("btn_settings.png");
    private final Image hoverOff_about =                getGraphics("btn_about.png");
    private final Image hoverOff_user =                 getGraphics("btn_user.png");
    private final Image hoverOff_server =               getGraphics("btn_logo.png");
    private final Image hoverOff_experimental =         getGraphics("btn_experimental.png");
    //////// HOVER IMAGES END ////////

    //////////////////////////////////
    // JavaFXML - GUI
    //////////////////////////////////
    @FXML
    private JFXTreeTableView<HotkeyMapWrapper> mapTreeView;
    @FXML
    private ImageView btn_open_menus, btn_minimize, btn_off, btn_general, btn_user, btn_about, btn_experimental,
                      btn_start_server;
    @FXML
    private JFXButton btn_cancel_general, btn_cancel_user, btn_cancel_experimental,
                      btn_save_general, btn_save_user_ghost, btn_save_user_create,
                      btn_load_experimental, btn_newmap, btn_cancel_map, btn_choose_programm,
                      btn_cancel_choose_programm, btn_select_choose_programm, btn_cancel_details_programm, btn_execute_programm,
                      btn_save_map, btn_add_hotkey, btn_post_map, btn_vote_wish, btn_vote_work;
    @FXML
    private AnchorPane menu_top, menu_sub, menu_general, menu_user, menu_experimental, mapsPane, menu_about, menu_programm,
                       menu_maps, head_programm, head_maps, head_details, menu_details;
    @FXML
    private JFXTextField tf_selected_programm, lbl_username, lbl_userpasswd_visible, tf_sc, tf_maps_prog, tf_maps_aktion, mapFilter,
                         tf_programmname, tf_author, tf_voteworks, tf_votewish, tf_stable, tf_keys;
    @FXML
    private JFXPasswordField lbl_userpasswd;
    @FXML
    private JFXTextArea ta_contributes;
    @FXML
    private JFXCheckBox cbx_user_show_pw, cbx_account_exists, cbx_account_new, cbx_release_map, cbx_new_map, cbx_extend_map;
    @FXML
    private JFXToggleButton toggle_load_experimental_map, toggle_autoupdate;
    ///////// JavaFXML END ///////////

    //////////////////////////////////
    // FUNCTIONS
    //////////////////////////////////

    /**
     * Command:
     * @param name Name of the Image
     * @return a new Image loaded by its name
     */
    private Image getGraphics(final String name){ return new Image(Main.class.getResourceAsStream("images/" + name)); }

    /**
     * Command: This Procedure acts as button-clicked for AnchorImages. In this program AnchorImages form
     * the main- and sub-menutoolbar.
     * @param mouseClicked Mouse-click which triggers this Event
     */
    @FXML
    private void handleButtonAction(final MouseEvent mouseClicked){

        final EventTarget target = mouseClicked.getTarget();

        if(target == btn_off){ System.exit(0); return; }
        if(target == btn_start_server) startServer();
        if(target == btn_minimize) minimizeAllSubmenus();
        else if(target == btn_open_menus) setVisibility(true, menu_sub, menu_general);
        else if(target == btn_select_choose_programm){
            setVisibility(false, menu_programm);
            setEnable(true, tf_selected_programm);
            mapTreeView.getSelectionModel().getSelectedItem();
        }
        else if(target == btn_about){
            minimizeAllSubmenus();
            setVisibility(true, menu_sub, menu_about);
        }
        else if(target == btn_user){
            minimizeAllSubmenus();
            setVisibility(true, menu_sub,menu_user);
        }
        else if(target == btn_general){
            minimizeAllSubmenus();
            setVisibility(true, menu_sub, menu_general);
        }
        else if(target == btn_experimental){
            minimizeAllSubmenus();
            setVisibility(true, menu_sub, menu_experimental);
        }
        else if(mouseClicked.getSource() == cbx_user_show_pw){
            if(!cbx_user_show_pw.isSelected()) {
                lbl_userpasswd_visible.setText(lbl_userpasswd.getText());
                lbl_userpasswd.setVisible(false);
                lbl_userpasswd_visible.setVisible(true);
            }
            if(cbx_user_show_pw.isSelected()) {
                lbl_userpasswd.setText(lbl_userpasswd_visible.getText());
                lbl_userpasswd.setVisible(true);
                lbl_userpasswd_visible.setVisible(false);
            }
        }
    }

    /**
     * Command: This Procedure handles the hover-in effect
     * @param mouseMovement Mouse movement  which triggers this Event
     */
    @FXML
    private void handleHoverBtnIn(final MouseEvent mouseMovement){
        final EventTarget target = mouseMovement.getTarget();
        if(target == btn_off)                btn_off.setImage(hoverOn_off);
        else if(target == btn_minimize)      btn_minimize.setImage(hoverOn_minimize);
        else if(target == btn_open_menus)    btn_open_menus.setImage(hoverOn_settings_and_general);
        else if(target == btn_about)         btn_about.setImage(hoverOn_about);
        else if(target == btn_user)          btn_user.setImage(hoverOn_user);
        else if(target == btn_general)       btn_general.setImage(hoverOn_settings_and_general);
        else if(target == btn_experimental)  btn_experimental.setImage(hoverOn_experimental);
    }

    /**
     * Command: This Procedure handles the hover-out effect
     * @param mouseMovement Mouse movement  which triggers this Event
     */
    @FXML
    private void handleHoverBtnOut(final MouseEvent mouseMovement){
        final EventTarget target = mouseMovement.getTarget();
        if(target == btn_off)                btn_off.setImage(hoverOff_off);
        else if(target == btn_minimize)      btn_minimize.setImage(hoverOff_minimize);
        else if(target == btn_open_menus)    btn_open_menus.setImage(hoverOff_settings_and_general);
        else if(target == btn_about)         btn_about.setImage(hoverOff_about);
        else if(target == btn_user)          btn_user.setImage(hoverOff_user);
        else if(target == btn_general)       btn_general.setImage(hoverOff_settings_and_general);
        else if(target == btn_experimental)  btn_experimental.setImage(hoverOff_experimental);
    }

    /**
     * Command: This Procedure handles the account action. If a user have provide
     * his credentials mark one of the checkboxes, the corresponding action is performed.
     * @param checkboxChecked ActionEvent which triggers this Event
     */
    @FXML
    private void handleAccountAction(final ActionEvent checkboxChecked){
        if (checkboxChecked.getSource() instanceof JFXCheckBox) {
            final String cbx = ((JFXCheckBox) checkboxChecked.getSource()).getText();

            if ("Neues Konto erstellen".equals(cbx)){
                btn_save_user_create.setText("Konto erstellen");
            }

            if ("Ich habe bereits ein Konto".equals(cbx)) {
                btn_save_user_create.setText("Anmelden");
            }
            setVisibility(true, btn_save_user_create);
            setVisibility(false, btn_save_user_ghost);
        }
    }

    @FXML
    private void handleToggleButton(final MouseEvent toggled){
        final Object source = toggled.getSource();
        if(source == toggle_load_experimental_map){
            if(!toggle_load_experimental_map.isSelected()) btn_load_experimental.setDisable(false);
            else btn_load_experimental.setDisable(true);
        }
    }

    /**
     * Command: Sets the visibility for a given set of Objects.
     * @param visible true sets objects visible, false will hide them
     * @param objects Must be Instance of AnchorPane or JFXButton
     */
    private void setVisibility(final boolean visible, final Object... objects){
        for(final Object obj : objects){
            if(obj instanceof AnchorPane)   ((AnchorPane) obj).setVisible(visible);
            if(obj instanceof JFXButton)    ((JFXButton) obj).setVisible(visible);
        }
    }

    /**
     * Command: Enable or disable a given set of Objects.
     * @param enabled true enables given objects, false will disable them
     * @param objects Must be an Instance of JFXCheckBox, JFXTextField or JFXToggleButton
     */
    private void setEnable(final boolean enabled, final Object... objects) {
        for (Object obj : objects) {
            if (obj instanceof JFXButton)       ((JFXButton) obj).setDisable(!enabled);
            if (obj instanceof JFXCheckBox)     ((JFXCheckBox) obj).setDisable(!enabled);
            if (obj instanceof JFXTextField)    ((JFXTextField) obj).setDisable(!enabled);
            if (obj instanceof JFXToggleButton) ((JFXToggleButton) obj).setDisable(!enabled);
        }
    }

    /**
     * Command: Sets new text to a given set of Objects.
     * @param text the text to set
     * @param objects Must be an Instance of JFXTextField or JFXButton
     */
    private void setText(final String text, final Object... objects){
        for (Object obj : objects) {
            if (obj instanceof JFXTextField)    ((JFXTextField) obj).setText(text);
            if (obj instanceof JFXButton)       ((JFXButton) obj).setText(text);
        }
    }

    /** Command: Tries to create and register the new user at REST-Service */
    @FXML
    private void createUser(){
        final User user = new User(lbl_username.getText(), lbl_userpasswd.getText());

        if(user.getName() == null || user.getName().isEmpty()){ createSnackbar(menu_user, "Bitte einen Benutzernamen eingeben");    return; }
        if(user.getName().contains(" ")){ createSnackbar(menu_user, "Benutzername darf keine Leerzeichen enthalten");     return; }
        if(user.getPasswd().length() < 8){ createSnackbar(menu_user, "Passwort muss aus mind. 8 Zeichen bestehen");       return; }

        createSnackbar(menu_user, new RestService().httpPost("register", user.userAsJSON()));
    }

    /**Command: Creates a Snackbar at the provided menu with a given message. Primary use is
     * visual feedback for info/error maessages.
     * @param menu the menu to show the message
     * @param message content of the Snackbar
     */
    private void createSnackbar(final AnchorPane menu, final String message){
        final JFXSnackbar snackbar = new JFXSnackbar(menu);
        snackbar.show(message, 4000);
    }

    /**
     * Command: Get the path of the JAR file.
     * @return the path of the JAR file
     */
    private File getPath(){
        return new File (Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }
    /**Command: Tries to load a File with the name settings.conf from root folder.*/
    private void loadConfig(){
        final File load = new File(getPath().getParentFile(), "settings.conf");

        final JSONParser parser = new JSONParser();

        try {
            final Object obj = parser.parse(new FileReader(load.toString().replace("%20", " ")));
            final JSONObject jsonObject = (JSONObject) obj;

            lbl_username.setText((String) jsonObject.get("username"));
            lbl_userpasswd.setText((String) jsonObject.get("userpasswd"));
            toggle_autoupdate.setSelected((boolean) jsonObject.get("autoupdate"));

            cbx_account_exists.setSelected(true);
            btn_save_user_create.setVisible(true);
            btn_save_user_create.setText("Anmelden");
            btn_save_user_ghost.setVisible(false);
        }
        catch (IOException | ParseException e) {createSnackbar(menu_top, "Config konnte nicht geladen werden");}
    }

    /**Command: Tries to load a File with the name: maps.json from root folder.
     * This file contain all maps that are available by the REST-Service.*/
    private HotkeyMapWrapper[] loadAllMaps(){
        final ArrayList<HotkeyMapWrapper> allMaps = new ArrayList<>();
        final JSONParser parser = new JSONParser();
        final File load = new File(getPath().getParentFile(), "maps.json");

        try {
            final Object obj = parser.parse(new FileReader(load.toString().replace("%20", " ")));
            final JSONObject jsonObject = (JSONObject) obj;
            for(Object map : jsonObject.values()){
                if(map instanceof JSONArray){
                    for (Object test : (JSONArray) map){
                        final JSONObject wholeMap = (JSONObject) test;
                        final JSONObject author = (JSONObject) wholeMap.get("author");
                        final JSONArray contributers = (JSONArray) wholeMap.get("contributer");
                        final HotkeyMap parsedMap = new HotkeyMap((String) wholeMap.get("destinationProgramName"));
                        final HashMap<String, String> keys = new HashMap<>();
                        keys.putAll((JSONObject)wholeMap.get("hotkeys"));

                        parsedMap.setAllKeys(keys);
                        parsedMap.setAuthor((String) author.get("name"));
                        parsedMap.setStable((Boolean) wholeMap.get("stable"));
                        parsedMap.setVoteWish((long) wholeMap.get("votedWish"));
                        parsedMap.setVoteWorks((long) wholeMap.get("votedWorks"));
                        parsedMap.setId((long) wholeMap.get("id"));

                        for(Object contributer : (JSONArray) contributers){
                            final JSONObject foundContributer = (JSONObject) contributer;
                            if(parsedMap.getContributer() == null)
                                parsedMap.setContributer(foundContributer.get("name") + "");
                            else
                                parsedMap.setContributer(parsedMap.getContributer() + ", " + foundContributer.get("name"));
                        }
                        allMaps.add(new HotkeyMapWrapper(parsedMap));
                    }
                }
            }
        }
        catch (IOException | ParseException e) {createSnackbar(menu_top, "Fehler beim lesen der Map-Datei");}

        return allMaps.toArray(new HotkeyMapWrapper[allMaps.size()]);
    }

    /**Command: Tries to save a File with the name settings.conf in root folder.
     * This file contains the user credentials and preferences.
     * @param menu where to show a message if saving has done properly
     */
    private void saveConfig(final AnchorPane menu){
        final JSONObject json = new JSONObject();
        final File save = new File(getPath().getParentFile(), "settings.conf");

        final String[] settings = {lbl_username.getText(), lbl_userpasswd.getText()};
        final boolean autoupdate = toggle_autoupdate.isSelected();

        json.put("username", settings[0]);
        json.put("userpasswd", settings[1]);
        json.put("autoupdate", autoupdate);

        try (FileWriter file = new FileWriter(save.toString().replace("%20", " "))) {
            file.write(json.toJSONString());
            file.flush();
            createSnackbar(menu, "Config gespeichert");

        } catch (IOException e) { createSnackbar(menu,"FEHLER! Config nicht gespeichert"); }
    }

    /**Command: Build the filterable collection of maps which are available. */
    private void mapTableTree(){
        final JFXTreeTableColumn<HotkeyMapWrapper, String> progName = new JFXTreeTableColumn<>("Programm");
        progName.setPrefWidth(215);
        progName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HotkeyMapWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HotkeyMapWrapper, String> param) {
                return param.getValue().getValue().programmnameProperty();
            }
        });

        final JFXTreeTableColumn<HotkeyMapWrapper, String> stableBoolean = new JFXTreeTableColumn<>("Stable");
        stableBoolean.setPrefWidth(119);
        stableBoolean.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<HotkeyMapWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<HotkeyMapWrapper, String> param) {
                return param.getValue().getValue().stableProperty();
            }
        });

        final ObservableList<HotkeyMapWrapper> maps = FXCollections.observableArrayList();
//        if(maps.addAll(loadAllMaps()));
        maps.addAll(loadAllMaps());

        final TreeItem<HotkeyMapWrapper> root = new RecursiveTreeItem<HotkeyMapWrapper>(maps, RecursiveTreeObject::getChildren);
        mapTreeView.getColumns().setAll(progName, stableBoolean);
        mapTreeView.setRoot(root);
        mapTreeView.setShowRoot(false);

        mapFilter.textProperty().addListener((o,oldVal,newVal)->{
            mapTreeView.setPredicate(map -> map.getValue().getProgrammname().contains(newVal) || map.getValue().getStable().contains(newVal));
        });
    }

    /**Command: Loads a HotkeyMap from disk
     * @param windowTitle Title of the Window
     */
    private void loadHotkeyMap(final String windowTitle){
        fileChooser("load", windowTitle);
    }

    /**Command: Saves all Maps provided from REST to local file. This file is used if no connection
     * to REST is available. Will update the maps file if autoupdate ist toggled on.
     */
    private void saveAllMapsLocally(){
        if(toggle_autoupdate.isSelected()){
            final JSONObject json = new JSONObject();
            json.put("Maps", new RestService().httpGet("maps"));
            if (json.get("Maps") == null) return;

            try{
                final File save = new File(getPath().getParentFile(), "maps.json");
                final FileWriter file = new FileWriter(save.toString().replace("%20", " "));
                file.write(json.toJSONString());
                file.flush();
            }
            catch (IOException e) {createSnackbar(menu_top, "Es trat ein Fehler beim speichern auf");}
        }
    }

    /**Command: Saves a map which was created by the user of this app (locally).
     * @param windowTitle Title of the Window
     */
    private void saveHotkeyMap(final String windowTitle){
        fileChooser("save", windowTitle);
    }

    /**Command: Provides a general FileLoader
     * @param load_or_save Tells the Fileloader either to load or save a file
     * @param windowTitle Title of FileLoader Window
     */
    private void fileChooser(final String load_or_save, final String windowTitle){
        final FileChooser chooser = new FileChooser();
        chooser.setTitle(windowTitle);
        final FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Single HotkeyApp Map", "*.json");
        chooser.getExtensionFilters().add(filter);

        if(load_or_save == "save"){
            final File selection = chooser.showSaveDialog(null);
            if(selection != null){
                final JSONObject map = new JSONObject();
                final JSONObject keys = new JSONObject();
                final User user = new User(lbl_username.getText(), lbl_userpasswd.getText());

                keys.putAll(selectedMap.getAllKeys());
                map.put("id", -1);
                map.put("destinationProgramName", selectedMap.getProgrammname());
                map.put("author", lbl_username.getText());
                map.put("hotkeys", keys);
                map.put("stable", selectedMap.getStable());

                try {
                    final FileWriter fileWriter = new FileWriter(selection + ".json");
                    fileWriter.write(map.toJSONString());
                    fileWriter.close();
                }
                catch (Exception e){createSnackbar(menu_experimental, "Es trat ein Fehler beim speichern auf");}
            }
        }
        else{
            final File selection = chooser.showOpenDialog(null);
            if(selection != null){
                    final JSONParser parser = new JSONParser();

                    try {
                        final Object obj = parser.parse(new FileReader(selection));
                        final JSONObject parsedMap = (JSONObject) obj;
                        final HotkeyMap map = new HotkeyMap();

                        map.setProgrammname((String) parsedMap.get("destinationProgramName"));
                        map.setId((long) parsedMap.get("id"));
                        map.setStable((Boolean) parsedMap.get("stable"));
                        map.setAllKeys((HashMap<String, String>) parsedMap.get("hotkeys"));

                        map.setAuthor((String) parsedMap.get("name"));

                        selectedMap = map;
                        setMapDetails();
                        setEnable(true, btn_execute_programm, tf_selected_programm);
                        setEnable(false, btn_vote_work, btn_vote_wish);
                        tf_selected_programm.setText(map.getProgrammname() + " - Lokal");
                    }
                    catch (IOException | ParseException e) {createSnackbar(menu_experimental, "FEHLER: Die Datei konnte nicht geladen werden");}
            }
        }
    }

    /**Command: Minimizes all submenus, only mainmenu is visible .*/
    private void minimizeAllSubmenus(){
        setVisibility(false, menu_sub, menu_general, menu_user, menu_about, mapsPane, menu_experimental, menu_details);
    }

    /**Command: Start the Server and wait for incomming commands.*/
    private void startServer(){
           if (selectedMap == null) {
            createSnackbar(menu_top, "Bitte eine Map auswählen");
            return;
        }
        if(!serverStarted) {
            serverStarted = true;
            btn_start_server.setImage(hoverOn_server);
            hotkeyServer.start(selectedMap);
        }
        else {
            serverStarted = false;
            btn_start_server.setImage(hoverOff_server);
            hotkeyServer.stop();
        }
    }

    /**Command: Sets all details of the map in the GUI.*/
    private void setMapDetails() {
        setText(selectedMap.getProgrammname(), tf_programmname, tf_selected_programm, tf_maps_prog);
        tf_author.setText(selectedMap.getAuthor());
        tf_votewish.setText("" + selectedMap.getVoteWish());
        tf_voteworks.setText("" + selectedMap.getVoteWorks());
        tf_stable.setText("" + selectedMap.getStable());
        tf_keys.setText("" + (selectedMap.getAllKeys()).size());
        ta_contributes.setText(selectedMap.getContributer());
    }

    /**Command: Initialize all buttons and checkboxes and link them to their needed procedures and methods.*/
    private void initButtons() {
        // Menu Buttons
        btn_cancel_general.setOnAction((e) -> minimizeAllSubmenus());
        btn_cancel_user.setOnAction((e) -> minimizeAllSubmenus());
        btn_cancel_experimental.setOnAction((e) -> minimizeAllSubmenus());
        btn_save_general.setOnAction((e) -> saveConfig(menu_general));
        btn_save_map.setOnAction((e) -> saveHotkeyMap("Save HotkeyMap"));
        btn_load_experimental.setOnAction((e) -> loadHotkeyMap("Load HotkeyMap"));
        btn_newmap.setOnAction((e) -> {
            setVisibility(true, menu_maps, mapsPane, head_maps);
            setVisibility(false, head_programm, menu_programm, head_details);
        });
        btn_choose_programm.setOnAction((e) -> {
            setVisibility(true, menu_programm, mapsPane, head_programm);
            setVisibility(false, head_maps, head_details, menu_details, menu_maps);
        });
        btn_execute_programm.setOnAction((e) -> {
            setVisibility(true, mapsPane, menu_details, head_details);
            setVisibility(false, head_maps, head_programm, menu_programm);
        });
        btn_cancel_details_programm.setOnAction((e) -> {
            setVisibility(false, mapsPane, menu_details);
        });
        btn_cancel_map.setOnAction((e) -> {
            setVisibility(false, mapsPane);
            //setEnable(false, tf_maps_aktion, tf_maps_prog);
            setText("", tf_maps_prog, tf_maps_aktion, tf_sc);
        });
        btn_cancel_choose_programm.setOnAction((e) -> {
            setVisibility(false, mapsPane,menu_programm);
        });
        btn_select_choose_programm.setOnAction((e) -> {
            final String progname = mapTreeView.getSelectionModel().getSelectedItem().getValue().getMap().getProgrammname();

            selectedMap = mapTreeView.getSelectionModel().getSelectedItem().getValue().getMap();
            if (selectedMap != null) {
                setEnable(true, btn_execute_programm, btn_vote_wish, btn_vote_work);
                setMapDetails();
                setText(progname, tf_selected_programm, tf_maps_prog);
            }
            setVisibility(false, mapsPane, menu_programm);
            if(selectedMap.getId() == -1)
                setEnable(false, btn_vote_wish, btn_vote_work);
        });
        btn_vote_wish.setOnAction((e) -> {
            final User user = new User(lbl_username.getText(), lbl_userpasswd.getText());
            createSnackbar(menu_details, new RestService().httpPost("map/" + selectedMap.getId() + "/vote/wish/", user.userAsJSON()));
        });
        btn_vote_work.setOnAction((e) -> {
            final User user = new User(lbl_username.getText(), lbl_userpasswd.getText());
            createSnackbar(menu_details, new RestService().httpPost("map/" + selectedMap.getId() + "/vote/works/", user.userAsJSON()));
        });
        btn_add_hotkey.setOnAction((e) -> {
            if(cbx_new_map.isSelected()) {
                if(ownMap.getAllKeys() != null ) ownMap.setAllKeys(new HashMap<>());

                selectedMap = ownMap;
                final String progname = tf_maps_prog.getText().trim();
                if (progname.isEmpty() || progname == null) {
                    createSnackbar(menu_maps, "Bitte einen Namen für das Programm angeben");
                    return;
                }
                ownMap.setProgrammname(progname);
                ownMap.setId(-1);
                setMapDetails();
                setEnable(false, btn_vote_work, btn_vote_wish, tf_maps_prog);
            }

            createSnackbar(menu_maps, selectedMap.addKey(new Hotkey(tf_maps_aktion.getText(), tf_sc.getText().toLowerCase())));
            cbx_new_map.setSelected(false);
            setText("", tf_maps_aktion, tf_sc);
            setEnable(true, btn_execute_programm);
            setMapDetails();
        });
        btn_post_map.setOnAction((e) -> {
            final JSONObject post = new JSONObject();
            final JSONObject author = new JSONObject();
            author.put("name", lbl_username.getText());
            author.put("passwd", lbl_userpasswd.getText());
            post.put("author", author);
            post.put("hotkeys", selectedMap.getAllKeys());
            post.put("destinationProgramName", selectedMap.getProgrammname());

            if(!cbx_extend_map.isSelected()) createSnackbar(menu_maps, new RestService().httpPost("map/new", post));
            else createSnackbar(menu_maps, new RestService().httpPost("map/addKeys", post));
        });

        // Checkboxen
        cbx_account_new.selectedProperty().addListener((observable, oldValue, newValue) -> cbx_account_exists.setSelected(!newValue));
        cbx_account_exists.selectedProperty().addListener((observable, oldValue, newValue) -> cbx_account_new.setSelected(!newValue));
        cbx_release_map.selectedProperty().addListener((observable, oldValue, newValue) -> {
            setEnable(newValue, btn_post_map, cbx_extend_map);
        });
        cbx_new_map.selectedProperty().addListener((observable, oldValue, newValue) -> {
            setEnable(newValue, tf_maps_prog);
            setText("", tf_maps_prog);
        });
    }
    //////// FUNCTIONS END ///////////

    /**
     * Command: Initialize all components of the GUI (Buttons, Checkboxes etc.)
     * @param location location of the resources
     * @param resources all resource files
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadConfig();
        saveAllMapsLocally();
        minimizeAllSubmenus();
        initButtons();
        mapTableTree();
    }
}
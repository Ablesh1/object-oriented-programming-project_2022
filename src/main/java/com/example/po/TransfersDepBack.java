package com.example.po;

public class TransfersDepBack extends Department{
    //@Override
    /*public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HallGUI.class.getResource("Transfers.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
    }*/

    public TransfersDepBack() {
        super();
        //System.out.println("Włąmczyłęm sięm");
    }

    @Override
    public void refresh(){
        try{
            //System.out.println("Transfers do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            return;
        }
    }
}

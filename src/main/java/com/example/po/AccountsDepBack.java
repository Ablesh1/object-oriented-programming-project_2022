package com.example.po;

public class AccountsDepBack extends Department{
  /*  @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HallGUI.class.getResource("Accounts.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Visual Novel");
        stage.setScene(scene);
        stage.show();
    }*/

    public AccountsDepBack(){
        super();
        System.out.println("Bruuuh");
    }

    @Override
    public void refresh(){
        try{
            //System.out.println("Accounts do calculations");
            Thread.sleep(2600);
        } catch (InterruptedException interruptedException) {
            return;
        }
    }
}

package project.Facade;
import project.exceptions.CompanyException;
import project.exceptions.CustomerException;
import project.exceptions.UserException;
import project.util.ClientType;




public class LoginManager {

    ClientFacade clientFacade = null;

    private static LoginManager instance = null;

    private LoginManager() {
    }

    public static LoginManager getInstance(){
                //check if our instance is null for creating it
                if (instance == null) {
                    //lock critical code, so no other thread can access the code
                    //while we creating the instance
                    //Thread Safe method bonus
                    synchronized (LoginManager.class) {
                        //double check, so we can be sure that no other thread
                        //already created our instance
                        if (instance == null) {
                            //create new instance of MySingleTon.MySingleTone class
                            instance = new LoginManager();
                        }
                    }
                }return instance;


        }

    /**
     *
     * @param email get email
     * @param password get password
     * @param clientType get client type
     * @return validation if information was correct
     * @throws UserException thrown if admin information
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws UserException {
        switch (clientType){
            case Administartor:
                AdminFacade af = new AdminFacade();
                if(af.login(email, password)){
                System.out.println("welcome admin");
                return af;
            }else{
                    throw new UserException("name or password are incorrect");
            }
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
                 if(companyFacade.login(email, password)){
                System.out.println("welcome company");
                return companyFacade;
            }else{
                throw new UserException("name or password are incorrect");
            }
            case Customer:
                CustomerFacade customerFacade= new CustomerFacade();
                if(customerFacade.login(email, password)){
                System.out.println("welcome customer");
               return customerFacade;
        }else{
            throw new UserException("name or password are incorrect");
        }
            default:
                throw new UserException("who are you?");
        }

    }
    }

package util;



import java.io.IOException;
import java.util.Calendar;

import model.Usuario;
import service.UserService;

public class ConfereLogin {
	private String login, senha;
	private UserService us;
	private Usuario user;
	private Log log;
	private String arquivo = "E:\\Praticas de Programacao Integrada\\Lab\\Aula 9 Ex 2\\WebContent\\log\\RegistroDeAcesso.txt";
    private String key = "PRATPROGINTEGRAD";

	public ConfereLogin(String login, String senha) {
		setLogin(login);
		setSenha(senha);
		us = new UserService();
		log = new Log();

	}

	public Usuario Confere() {
		if(Busca(getLogin()) == true) {
			String confere = getSenha();
			String SenhaUser = senhaUsuario(user.getSenha());
			if(SenhaUser.equals(confere)) {
				Calendar c = Calendar.getInstance();
				try {
					log.abrir(arquivo);
					log.escrever("\nUsuario: "+getLogin()+" Fez acesso : "+c.getTime()+"\n\n");
					log.fechar();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return user;
			}else {
				Calendar c = Calendar.getInstance();
				try {
					log.abrir(arquivo);
					log.escrever("\nUsuario: "+getLogin()+" errou a senha"+getSenha()+"\n Fez acesso : "+c.getTime()+"\n\n");
					log.fechar();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}
		return null;
	}

	public boolean Busca(String login){
		user = us.carregarLogin(login);
		if(user != null) {
			return true;
		}else {
			try {
				Calendar c = Calendar.getInstance();
				log.abrir(arquivo);
				log.escrever("\nUsuario: "+login+" Nao encontrado \n Tentou acesso : "+c.getTime()+"\n\n");
				log.fechar();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	
	public String senhaUsuario(byte[] userPass) {
		String decriptou = "";
		try {
			byte[] uDecripto = CryptoUtils.decrypt(key, userPass);
			 decriptou = new String(uDecripto, "UTF-8");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decriptou;
	}

	
	public String getLogin() {
		return login;
	}

	public String getSenha(){
		return senha;
	}
	
	
	public void setLogin(String login) {
		this.login = login;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}

}

package LOGIC;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import java.util.Set;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;

public class KeyRing {
	
	private PGPPublicKeyRing myPublicKeyRing;
	private PGPSecretKeyRing mySecretKeyRing;
	public KeyRing() {
		myPublicKeyRing = null;
		mySecretKeyRing = null;
	}
	
	public void setPublicKeyRing(PGPPublicKeyRing publicKeyRing) {
		myPublicKeyRing = publicKeyRing;
	}

	public void setSecretKeyRing(PGPSecretKeyRing secretKeyRing) {
		mySecretKeyRing = secretKeyRing;
	}
	
	public PGPSecretKeyRing getSecretKeyRing() {
		return this.mySecretKeyRing;
	}
	
	public PGPPublicKeyRing getPublicKeyRing() {
		return myPublicKeyRing;
	}
	
	public String toString() {
		String str = "";
		if (myPublicKeyRing != null)
			str += myPublicKeyRing.getPublicKey().getKeyID() + " " + myPublicKeyRing.getPublicKey().getUserIDs().next();
		else
		if (mySecretKeyRing != null)
			str += mySecretKeyRing.getSecretKey().getKeyID() + " " + mySecretKeyRing.getSecretKey().getUserIDs().next();
		
		return str;
	}
	
	public Pair<Long, String> getIds()
	{
		long keyId = 0;
		String userId = null;
		
		int i=0; 
		if (myPublicKeyRing != null) {
			keyId = myPublicKeyRing.getPublicKey().getKeyID();
			userId = myPublicKeyRing.getPublicKey().getUserIDs().next();
			i=1;
		}
		
		if (mySecretKeyRing != null) {
			keyId = mySecretKeyRing.getSecretKey().getKeyID();
			userId = mySecretKeyRing.getSecretKey().getUserIDs().next();
			i =(i==1)?3:2;
		}
		userId += i;
		
		return new Pair<Long, String>(keyId, userId);
	}
	
	public Long getKeyId() {
		Long k = (long) 0; 
		if (myPublicKeyRing != null) {
			k = myPublicKeyRing.getPublicKey().getKeyID();
		}
		
		if (mySecretKeyRing != null) {
			k = mySecretKeyRing.getSecretKey().getKeyID();
		}
		return k;
	}
	
	public boolean matchPassword(String password) {
        try {
        	System.out.println(this.getKeyId().toString());
			PGPPrivateKey privateKey = mySecretKeyRing.getSecretKey().extractPrivateKey(
					new JcePBESecretKeyDecryptorBuilder().setProvider(BouncyCastleProvider.PROVIDER_NAME).build(password.toCharArray()));
			System.out.println("ok je");
			return true;
		} catch (PGPException e) {
			return false;
		}
	}
	
	public boolean exportPublicKey() {
		
		BufferedOutputStream buff;
		try {
			buff = new BufferedOutputStream(new FileOutputStream(new File("").getAbsolutePath() + "/public/public_"+this.getKeyId().toString()+".asc"));

			ArmoredOutputStream privateOut = new ArmoredOutputStream(buff);
			myPublicKeyRing.encode(privateOut);
			privateOut.close();
			buff.close();
			return true;
		} catch (Exception e) {
		
			return false;
		}
	}
	
	public boolean exportSecretKey() {
		
		BufferedOutputStream buff;
		try {
			buff = new BufferedOutputStream(new FileOutputStream(new File("").getAbsolutePath() + "/secret/secret_"+this.getKeyId().toString()+".asc"));

			ArmoredOutputStream privateOut = new ArmoredOutputStream(buff);
			mySecretKeyRing.encode(privateOut);
			privateOut.close();
			buff.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}

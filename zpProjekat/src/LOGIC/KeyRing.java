package LOGIC;

import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import java.util.Set;

import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;

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
		
		if (myPublicKeyRing != null) {
			keyId = myPublicKeyRing.getPublicKey().getKeyID();
			userId = myPublicKeyRing.getPublicKey().getUserIDs().next();
		}
		else
		if (mySecretKeyRing != null) {
			keyId = mySecretKeyRing.getSecretKey().getKeyID();
			userId = mySecretKeyRing.getSecretKey().getUserIDs().next();
		}
		
		return new Pair<Long, String>(keyId, userId);
	}
}

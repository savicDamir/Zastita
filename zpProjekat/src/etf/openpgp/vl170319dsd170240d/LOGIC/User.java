package etf.openpgp.vl170319dsd170240d.LOGIC;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;

import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRing;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;
import org.bouncycastle.util.encoders.Base64Encoder;

public class User {
	//registrujem providera
	static {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}
	private static Provider provider = new BouncyCastleProvider();
	public User() {
		//za pocetak treba da ucitam sve kljuceve koje imam to radim kad me napravi u mejnu
		System.out.println("User");
		
	}

	public void generateNewKey(String name, String email, String type, String password) {
		
		int keySize = Integer.parseInt(type);
		String userInfo =  name+"<"+email+">";
		
		PGPKeyRingGenerator keyRingGenerator = Key.generateNewKey(userInfo, keySize, password);
		
		KeyRing keyRing = new KeyRing();
		keyRing.setPublicKeyRing(keyRingGenerator.generatePublicKeyRing());
		keyRing.setSecretKeyRing(keyRingGenerator.generateSecretKeyRing());
		
		Key.keyRingList.add(keyRing);
	}
}

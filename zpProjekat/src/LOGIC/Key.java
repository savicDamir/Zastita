package LOGIC;


import javafx.util.Pair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;

public class Key {


	public static ArrayList<KeyRing> keyRingList = new ArrayList<KeyRing>();
	private static Provider provider = new BouncyCastleProvider();
	
	
	
	public static PGPKeyRingGenerator generateNewKey(String userInfo, int keySize, String password) {
	
		KeyPairGenerator generator;
		try {
			//java key par koji je samo holder
			generator = KeyPairGenerator.getInstance("RSA", provider);
			generator.initialize(keySize);
	        KeyPair pair = generator.generateKeyPair();
	        
	        System.out.println(pair.getPublic());
	        //sad ide bc key par
	        PGPKeyPair pgpPair =  new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, pair, new Date());
	       
	        //sada nam treba key ring
	        PGPDigestCalculator sha1calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);

			PGPKeyRingGenerator keyRingGenerator = new PGPKeyRingGenerator(PGPSignature.POSITIVE_CERTIFICATION, pgpPair,
					userInfo, sha1calc, null, null,
					new JcaPGPContentSignerBuilder(pgpPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1),
					new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.CAST5, sha1calc).setProvider(provider) //ovde ako je sranje provider 
							.build(password.toCharArray()));
			
			return keyRingGenerator;			
	        /*PGPPublicKeyRing publicKey = keyRingGenerator.generatePublicKeyRing();
	     
	        BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream("javni"));

			ArmoredOutputStream privateOut = new ArmoredOutputStream(buff);
			publicKey.encode(privateOut);
			privateOut.close();
			buff.close();
`			*/	     
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void storeAllKeyRings() {

		List<byte[]> allKeyRings = new ArrayList<byte[]>();
		for (KeyRing k : keyRingList) {
			allKeyRings.addAll(getEncoded(k.getPublicKeyRing(), k.getSecretKeyRing()));
		}
		
		ObjectOutputStream storage;
		try {
			storage = new ObjectOutputStream(new FileOutputStream(new File("").getAbsolutePath() + "/keys/AllKeys.dat"));
			storage.writeObject(allKeyRings);
			storage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<byte[]> getEncoded(PGPPublicKeyRing publicKeyRing, PGPSecretKeyRing secretKeyRing) {
		
		try {
			byte[] encodedSecretKeyRing = (secretKeyRing == null) ? new byte[0] : secretKeyRing.getEncoded();
			byte[] encodedPublicKeyRing = (publicKeyRing == null) ? new byte[0] : publicKeyRing.getEncoded();

			return Arrays.asList(encodedPublicKeyRing, encodedSecretKeyRing);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void loadAllKeyRings() {
		try {
			if (Files.exists(Paths.get(new File("").getAbsolutePath() + "/keys/AllKeys.dat"))) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("").getAbsolutePath() + "/keys/AllKeys.dat"));
				List<byte[]> encodedKeyRings = (List<byte[]>) in.readObject();

				for (int i = 0; i < encodedKeyRings.size(); i += 2) {
					keyRingList.add(decodeKeyRing(encodedKeyRings.get(i), encodedKeyRings.get(i + 1)));
				}

				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(keyRingList.size());
	}
	
	public static KeyRing decodeKeyRing(byte[] publicKeyRing, byte[] secretKeyRing) {
		KeyRing k = new KeyRing();
		
		
		try {
			if (publicKeyRing.length > 0)
				k.setPublicKeyRing(new PGPPublicKeyRing(publicKeyRing, new JcaKeyFingerprintCalculator()));
			if (secretKeyRing.length > 0)
				k.setSecretKeyRing(new PGPSecretKeyRing(secretKeyRing, new JcaKeyFingerprintCalculator()));
			
		} catch (Exception e) {
				e.printStackTrace();
		}
			
		return k;
	}
	
	public static String getAllKeyRingStrings()
	{
		String str = "";
		for (KeyRing k : keyRingList) {
			str += k.toString() + "\n";
		}
		return str;
	}
	
	public static List<Pair<Long, String>> getAllKeyRing(){
		List<Pair<Long, String>> lista = new ArrayList<Pair<Long, String>>();
		for (KeyRing k : keyRingList) {
			lista.add(k.getIds());
		}
		return lista;
	}
	public static int numberOfKeys() {
		return keyRingList.size();
	}
	
	public static void deleteKey(Long id) {
		for (KeyRing k : keyRingList) {
			if (k.getKeyId().equals(id))
				keyRingList.remove(k);
		}
	}
	
	public static boolean deleteKeyPassword(Long id, String password) {
		for (KeyRing k : keyRingList) {
			if (k.getKeyId().equals(id)) {
				if (k.matchPassword(password)){
					keyRingList.remove(k);
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public static boolean exportPublicKey(Long id) {
		for (KeyRing k : keyRingList) {
			if (k.getKeyId().equals(id)) {
				return k.exportPublicKey(); 
			}
		}
		return false;
	}
	
	public static boolean exportSecretKey(Long id) {
		for (KeyRing k : keyRingList) {
			if (k.getKeyId().equals(id)) {
				return k.exportSecretKey();
			}
		}
		return false;
	}
}

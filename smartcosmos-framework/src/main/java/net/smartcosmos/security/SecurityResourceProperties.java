package net.smartcosmos.security;

import lombok.Data;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * Represents the configuration properties for Smart Cosmos Security.
 *
 * @author voor
 */
@Data
@ConfigurationProperties("smartcosmos.security.resource")
public class SecurityResourceProperties {

	private KeyStoreProperties keystore = new KeyStoreProperties();
	private UserDetailsProperties userDetails = new UserDetailsProperties();

	private String clientId;
	private String clientSecret;

	@Data
	public class KeyStoreProperties {
		private Resource location;
		private char[] password;
		private String keypair;
		private char[] keypairPassword;
	}

	@Data
	public class UserDetailsProperties {
		private SecurityProperties.User user = new SecurityProperties.User();
		private UserDetailsServerProperties server = new UserDetailsServerProperties();
	}

	@Data
	public class UserDetailsServerProperties {
		private String locationUri;
	}
}
package br.com.authentication.security.type;

import lombok.Getter;

@Getter
public enum AuthConfig {
	GERENCIADOR_PRINCIPAL("UUtYGqgzPAoSZgFDIjNFdNky3l23XAAe"),
	ATENTIMENTOS("4z7NgTHNQtmpbSxZeXREXbjCexFSCZTk"),
	SECRET_KEY("UUtYGqgzPAoSZgFDIjNFdNky3l23XAZZ"),
	EXPIRE_TIME("14400000");
	
	private final String config;

	AuthConfig(String config) {
		 this.config = config;
	}

}

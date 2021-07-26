package com.macro.cloud.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 17:23
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "oauth2.client")
public class ClientsInitConfig implements InitializingBean {

    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    private String clientId;

    private String clientSecret;

    private String scope;

    private List<String> grantTypes;

    private Integer accessTokenValidTime;

    private Integer refreshTokenValidTime;

    @Override
    public void afterPropertiesSet() throws Exception {
        BaseClientDetails client = new BaseClientDetails();
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);
        client.setScope(Arrays.asList(scope));
        client.setAuthorizedGrantTypes(grantTypes);
        client.setAccessTokenValiditySeconds(accessTokenValidTime);
        client.setRefreshTokenValiditySeconds(refreshTokenValidTime);

        ClientDetails clientDetails = null;
        try {
            clientDetails = clientDetailsService.loadClientByClientId(clientId);
        } catch (InvalidClientException e) {
            clientDetailsService.addClientDetails(client);
        }
        clientDetailsService.updateClientDetails(client);
        log.info("init client {} finished !!!",client);
    }
}

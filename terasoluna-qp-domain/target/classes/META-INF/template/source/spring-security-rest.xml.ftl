	<!-- This is default url to get a token from OAuth -->
	<http pattern="/oauth/token" create-session="stateless"
		authentication-manager-ref="clientAuthenticationManager"
		xmlns="http://www.springframework.org/schema/security">
		<intercept-url pattern="/oauth/token" access="IS_AUTHENTICATED_FULLY" />
		<anonymous enabled="false" />
		<http-basic entry-point-ref="clientAuthenticationEntryPoint" />
		<!-- include this only if you need to authenticate clients via request parameters -->
		<custom-filter ref="clientCredentialsTokenEndpointFilter"
			after="BASIC_AUTH_FILTER" />
		<access-denied-handler ref="oauthAccessDeniedHandler" />
	</http>

	<!-- This is where we tells spring security what URL should be protected and what roles have access to them -->
	<http pattern="/${url-pattern/authen}/**" create-session="never"
		entry-point-ref="oauthAuthenticationEntryPoint"
		access-decision-manager-ref="accessDecisionManager"
		xmlns="http://www.springframework.org/schema/security">
		<anonymous enabled="false" />
		
		
		<!-- appendWSIntercept -->
		
		<custom-filter ref="resourceServerFilter" before="PRE_AUTH_FILTER" />
		<access-denied-handler ref="oauthAccessDeniedHandler" />
	</http>

	<bean id="oauthAuthenticationEntryPoint"
		class="org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint">
		<property name="realmName" value="test" />
	</bean>

	<bean id="clientAuthenticationEntryPoint"
		class="org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint">
		<property name="realmName" value="test/client" />
		<property name="typeName" value="Basic" />
	</bean>

	<bean id="oauthAccessDeniedHandler"
		class="org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler" />

	<!-- <bean id="clientCredentialsTokenEndpointFilter"
		class="org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter">
		<property name="authenticationManager" ref="clientAuthenticationManager" />
	</bean> -->
	
	<bean id="clientCredentialsTokenEndpointFilter"
		class="org.terasoluna.qp.app.common.ultils.ClientCredentialsTokenEndpointFilterEx">
		<property name="authenticationManager" ref="clientAuthenticationManager" />
	</bean>

	<bean id="accessDecisionManager" class="org.springframework.security.access.vote.UnanimousBased"
		xmlns="http://www.springframework.org/schema/beans">
		<constructor-arg>
			<list>
				<bean class="org.springframework.security.oauth2.provider.vote.ScopeVoter" />
				<bean class="org.springframework.security.access.vote.RoleVoter" />
				<bean class="org.springframework.security.access.vote.AuthenticatedVoter" />
			</list>
		</constructor-arg>
	</bean>

	<authentication-manager id="clientAuthenticationManager"
		xmlns="http://www.springframework.org/schema/security">
		<authentication-provider user-service-ref="clientDetailsUserService" />
	</authentication-manager>


	<!-- This is simple authentication manager, with a hardcoded user/password 
		combination. We can replace this with a user defined service to get few users 
		credentials from DB -->
	<!-- <authentication-manager alias="authenticationManager"
		xmlns="http://www.springframework.org/schema/security">
		<authentication-provider >
			<user-service id="userDetailsService">
				<user  name="admin" password="admin" authorities="ROLE_APP" />
			</user-service>
		</authentication-provider>
	</authentication-manager> -->

	<bean id="clientDetailsUserService"
		class="org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService">
		<constructor-arg ref="clientDetails" />
	</bean>


	<!-- This defined token store, we have used inmemory tokenstore for now but this can be changed to a user defined one -->
	<bean id="tokenStore"
		class="org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore" />

	<!-- This is where we defined token based configurations, token validity and other things -->
	<bean id="tokenServices"
		class="org.springframework.security.oauth2.provider.token.DefaultTokenServices">
		<property name="tokenStore" ref="tokenStore" />
		<property name="supportRefreshToken" value="true" />
		<property name="accessTokenValiditySeconds" value="120" />
		<property name="clientDetailsService" ref="clientDetails" />
	</bean>

	<bean id="userApprovalHandler"
class="org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler">
		<property name="tokenStore" ref="tokenStore" />
		<property name="requestFactory" ref="oAuth2RequestFactory" />
	</bean>

	<bean id="oAuth2RequestFactory"
        class="org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory">
        <constructor-arg ref="clientDetails" />
    </bean>

	<oauth:authorization-server
		client-details-service-ref="clientDetails" token-services-ref="tokenServices"
		user-approval-handler-ref="userApprovalHandler">
		<oauth:authorization-code />
		<oauth:implicit />
		<oauth:refresh-token />
		<oauth:client-credentials />
		<oauth:password />
	</oauth:authorization-server>

	<oauth:resource-server id="resourceServerFilter"
		resource-id="test" token-services-ref="tokenServices" />

	<oauth:client-details-service id="clientDetails">
		<!-- client -->
		<!-- <oauth:client client-id="restapp"
			authorized-grant-types="authorization_code,client_credentials"
			authorities="ROLE_APP" scope="read,write,trust" secret="secret" /> -->

		<oauth:client client-id="${clientId}"
			authorized-grant-types="password,authorization_code,client_credentials,refresh_token,implicit"
			secret="${clientSecret}" authorities="ROLE_APP" />	

	</oauth:client-details-service>

	<sec:global-method-security
		pre-post-annotations="enabled" proxy-target-class="true">
		<!--you could also wire in the expression handler up at the layer of the http filters. See https://jira.springsource.org/browse/SEC-1452 -->
		<sec:expression-handler ref="oauthExpressionHandler" />
	</sec:global-method-security>

	<oauth:expression-handler id="oauthExpressionHandler" />
	<oauth:web-expression-handler id="oauthWebExpressionHandler" />
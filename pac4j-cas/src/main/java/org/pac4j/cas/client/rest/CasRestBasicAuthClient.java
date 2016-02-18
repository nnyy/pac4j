/*
 * Copyright 2012 - 2015 pac4j organization
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.pac4j.cas.client.rest;

import org.pac4j.cas.credentials.authenticator.CasRestAuthenticator;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.credentials.extractor.BasicAuthExtractor;
import org.pac4j.core.util.CommonHelper;

/**
 * Direct client which receives credentials as a basic auth and validates them via the CAS REST API.
 *
 * @author Misagh Moayyed
 * @since 1.8.0
 */
public class CasRestBasicAuthClient extends AbstractCasRestClient {

    private String casServerPrefixUrl;

    private String headerName = HttpConstants.AUTHORIZATION_HEADER;

    private String prefixHeader = HttpConstants.BASIC_HEADER_PREFIX;

    public CasRestBasicAuthClient() {}

    public CasRestBasicAuthClient(final String casServerPrefixUrl) {
        this.casServerPrefixUrl = casServerPrefixUrl;
    }

    public CasRestBasicAuthClient(final Authenticator authenticator) {
        setAuthenticator(authenticator);
    }

    public CasRestBasicAuthClient(final String casServerPrefixUrl,
                                  final String headerName, final String prefixHeader) {
        this.casServerPrefixUrl = casServerPrefixUrl;
        this.headerName = headerName;
        this.prefixHeader = prefixHeader;
    }

    public CasRestBasicAuthClient(final Authenticator authenticator,
                                  final String headerName, final String prefixHeader) {
        setAuthenticator(authenticator);
        this.headerName = headerName;
        this.prefixHeader = prefixHeader;
    }

    @Override
    protected void internalInit(final WebContext context) {
        CommonHelper.assertNotBlank("headerName", this.headerName);
        CommonHelper.assertNotNull("prefixHeader", this.prefixHeader);
        if (CommonHelper.isNotBlank(this.casServerPrefixUrl)) {
            setAuthenticator(new CasRestAuthenticator(this.casServerPrefixUrl));
        }
        setExtractor(new BasicAuthExtractor(this.headerName, this.prefixHeader, getName()));
        super.internalInit(context);
        assertAuthenticatorTypes(CasRestAuthenticator.class);
    }

    public String getCasServerPrefixUrl() {
        return casServerPrefixUrl;
    }

    public void setCasServerPrefixUrl(String casServerPrefixUrl) {
        this.casServerPrefixUrl = casServerPrefixUrl;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getPrefixHeader() {
        return prefixHeader;
    }

    public void setPrefixHeader(String prefixHeader) {
        this.prefixHeader = prefixHeader;
    }

    @Override
    public String toString() {
        return CommonHelper.toString(this.getClass(), "name", getName(), "headerName", this.headerName,
                "prefixHeader", this.prefixHeader, "extractor", getExtractor(), "authenticator", getAuthenticator(),
                "profileCreator", getProfileCreator());
    }
}

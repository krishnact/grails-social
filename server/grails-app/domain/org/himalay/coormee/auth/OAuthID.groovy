/*
 * Copyright 2012 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.himalay.coormee.auth

import groovy.util.logging.Slf4j

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id;



/**
 * Simple domain class that records the identities of users authenticating via
 * an OAuth provider. Each identity consists of the OAuth account name and the
 * name of the provider. It also has a reference to the corresponding Spring Security
 * user account, although only long IDs are supported at the moment.
 */
@Slf4j
class OAuthID implements Serializable {

    String provider
    String accessToken
    String socialId

    static belongsTo = [user: User]

    static constraints = {
        accessToken unique: true
        socialId nullable: true
    }

    static mapping = {
        provider    index: "identity_idx"
        accessToken index: "identity_idx"
    }

    static OAuthID findByProviderAndAccessToken(String providerName, String socialId){

        OAuthID oAuthID = OAuthID.findBySocialIdAndProvider(socialId,providerName)
        if (oAuthID == null){
            log.info("No account found for ${socialId} from ${providerName}")
        }else{
            log.info("Ys account found for ${socialId} from ${providerName}, ${oAuthID.id}")
        }
        return oAuthID;
    }
}
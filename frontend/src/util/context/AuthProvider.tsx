import React, { ReactNode, createContext, useState } from 'react'
import { authContextData } from '../init/init';
import { AuthDataType } from '../type/types.shared';



type AuthContextType = {
    authData : AuthDataType,
    getCSRFHeader : () => { 'X-XSRF-TOKEN': string } | any,
    updateCSRF : (csrf : string) => void
    updateAuthentication : () => void
    invalidAuthentication : () => void
    invalidAuthData : () => void
}

export const AuthContext = createContext<AuthContextType>({
    authData : authContextData,
    getCSRFHeader : () => undefined,
    updateCSRF : () => undefined,
    updateAuthentication : () => undefined,
    invalidAuthentication : () => undefined,
    invalidAuthData : () => undefined
});

const AuthProvider = ( { children } : { children : ReactNode}) => {
    const [authData, setAuthData] = useState<AuthDataType>(authContextData);
    const getCSRFHeader = () => {
        return {
            'X-XSRF-TOKEN' : authData.csrf
        }
    }

    const updateCSRF = (csrf : string) => {
        setAuthData(prev => ({
            ...prev,
            csrf
        }))
    }

    const updateAuthentication = () => {
        setAuthData(prev => ({
            ...prev,
            isAuthenticated : true
        }))
    }

    const invalidAuthentication = () => {
        setAuthData(prev => ({
            ...prev,
            isAuthenticated : false
        }))
    }
    
    const invalidAuthData = () => {
        setAuthData(authContextData)
    }

    return (
        <AuthContext.Provider value={{authData, getCSRFHeader, updateCSRF, updateAuthentication, invalidAuthentication, invalidAuthData}}>
            { children }
        </AuthContext.Provider>
    )

}

export default AuthProvider
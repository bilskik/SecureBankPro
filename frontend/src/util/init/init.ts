import {  TransferType, UserDataType, UserDetailsType, AuthDataType } from "../type/types.shared";

export const initTransferData : TransferType = {
    transferTitle : "",
    senderName : "",
    senderAccNo : "",
    receiverName : "",
    receiverAccNo : "",
    amount : 0,
}

export const authContextData : AuthDataType= {
    isAuthenticated : false,
    csrf : ""
}

export const initDisplayedDetails : UserDetailsType = {
    creditCardNo : "",
    pesel : ""
}
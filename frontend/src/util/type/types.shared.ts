import { TransferKind } from "../reducer/transferReducer"

export type UserDataType = {
    accountNo : string,
    balance : number
}
export type AuthDataType = {
    isAuthenticated : boolean,
    csrf : string
}
export type TransferType = {
    transferTitle : string,
    senderName : string,
    senderAccNo : string,
    receiverName : string,
    receiverAccNo : string,
    amount : number,
}
export type TransferActionType = {
    type: TransferKind,
    payload : string | number
  }

export type UserDetailsType = {
    creditCardNo : string,
    pesel : string
}

export type ErrorType = {
    isError : boolean,
    message : string
}
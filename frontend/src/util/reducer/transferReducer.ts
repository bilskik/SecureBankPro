import { TransferActionType, TransferType } from "../type/types.shared"

export enum TransferKind {
    SENDER_NAME = "SENDER_NAME",
    SENDER_ACCNO = "SENDER_ACCNO",
    RECEIVER_NAME = "RECEIVER_NAME",
    RECEIVER_ACCNO = "RECEIVER_ACCNO",
    TRANSFER_AMOUNT = "TRANSFER_AMOUNT",
    TRANSFER_TITLE = "TRANSFER_TITLE",
  }
  

export const transferReducer = (state : TransferType, action : TransferActionType) => {
    const { type, payload } = action
    switch(type) {
        case TransferKind.SENDER_NAME:
          return {
            ...state,
            senderName : payload
          } as TransferType
        case TransferKind.SENDER_ACCNO:
            return {
              ...state,
              senderAccNo : payload
            } as TransferType
        case TransferKind.RECEIVER_NAME:
            return { 
                ...state,
                receiverName : payload
            } as TransferType
        case TransferKind.RECEIVER_ACCNO:
            return {
              ...state,
              receiverAccNo : payload
            } as TransferType
        case TransferKind.TRANSFER_AMOUNT:
            return {
              ...state,
              amount : payload
            } as TransferType
        case TransferKind.TRANSFER_TITLE:
            return {
              ...state,
              transferTitle : payload
            } as TransferType
        default:
          return state
    }
  }
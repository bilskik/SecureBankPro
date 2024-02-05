import React from 'react'
import { TransferType } from '../../util/type/types.shared'
import { Table } from 'react-bootstrap'

type TransferHistoryComp = {
    transferHistory : TransferType[] | undefined
}

const TransferHistory = ({ transferHistory } : TransferHistoryComp) => {
    return (
            transferHistory ? (
            <Table className='mt-3'>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Sender name</th>
                        <th>Sender account number</th>
                        <th>Receiver name</th>
                        <th>Receiver account number</th>
                        <th>Amount</th>
                    </tr>
                </thead>
                <tbody>
                    { transferHistory?.map((currTransfer : TransferType, index) => (
                        <tr key={index}>
                            <td>{ currTransfer.transferTitle }</td>
                            <td>{ currTransfer.senderName }</td>
                            <td>{ currTransfer.senderAccNo }</td>
                            <td>{ currTransfer.receiverName }</td>
                            <td>{ currTransfer.receiverAccNo }</td>
                            <td>{ currTransfer.amount }</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
            ) : null
        )
}

export default TransferHistory
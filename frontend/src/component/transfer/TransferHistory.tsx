import React from 'react'
import { TransferHistoryType } from '../../util/type/types.shared'
import { Table } from 'react-bootstrap'

type TransferHistoryComp = {
    transferHistory : TransferHistoryType[] | undefined
}

const TransferHistory = ({ transferHistory } : TransferHistoryComp) => {
    return (
            transferHistory ? (
            <Table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Sender name</th>
                        <th>Sender account number</th>
                        <th>Receiver name</th>
                        <th>Receiver account number</th>
                    </tr>
                </thead>
                <tbody>
                    { transferHistory?.map((currTransfer : TransferHistoryType, index) => (
                        <tr key={index}>
                            <td>{ currTransfer.transferTitle }</td>
                            <td>{ currTransfer.senderName }</td>
                            <td>{ currTransfer.senderAccNo }</td>
                            <td>{ currTransfer.receiverName }</td>
                            <td>{ currTransfer.receiverAccNo }</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
            ) : null
        )
}

export default TransferHistory
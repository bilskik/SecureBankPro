export const loginBeginValidator = (val : string) => {
    return val.trim() 
}
export const loginFinishValidator = ({ username, password } : { username : string, password : string}) => {
    return username.trim() && password.trim()
}
export const sendEmailValidator = ({ username, email} : { username : string, email : string }) => {
    return username.trim() && email.trim()
}
export const resetPasswordValidator = ({ username, email, password} : { username : string, email : string, password : string }) => {
    return username.trim() && email.trim() && password.trim()
}
export const formAmountValidator = (amount : number) => {
    return amount >= 1;
}
export const formAccValidator = (accNo : string) => {
    const accNoRegex = /^\d{26}$/;
    return accNoRegex.test(accNo);
}
import LoggedUserContext from "./LoggedUserContext.js";
import {useState} from "react";

const LoggedUserProvider = ({children}) => {
    const [loggedUSer, setLoggedUSer] = useState(null)

    const contextValue = {
        loggedUser: loggedUSer,
        setLoggedUser: setLoggedUSer
    }
    return <LoggedUserContext value={contextValue}>{children}</LoggedUserContext>
}

export default LoggedUserProvider;
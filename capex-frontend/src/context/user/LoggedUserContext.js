import {createContext} from "react";

const initialContext = {
    loggedUser: null,
    setLoggedUser: () => {}
}
const LoggedUserContext = createContext(initialContext);

export default LoggedUserContext;
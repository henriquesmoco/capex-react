import {Dialog} from "primereact/dialog";
import Button from "./Button.jsx";
import {PiUserSwitch} from "react-icons/pi";
import {useContext, useRef, useState} from "react";
import LoggedUserContext from "../context/user/LoggedUserContext.js";
import useAllUsers from "../hooks/useAllUsers.js";
import {Skeleton} from "primereact/skeleton";
import {Dropdown} from "primereact/dropdown";
import {Toast} from "primereact/toast";

const SwitchUserDialog = ({visible, onHide}) => {
    const {loggedUser, setLoggedUser} = useContext(LoggedUserContext);
    const [selectedUser, setSelectedUser] = useState(loggedUser)
    const {isLoading, data, error} = useAllUsers()
    const toast = useRef(null);

    const handleSwitchSelected = (e) => {
        setSelectedUser(e.value)
    }

    const handleSwitchUserConfirmed = () => {
        setLoggedUser(selectedUser)
        onHide()
    }

    if (error) {
        toast.current.show({severity: 'error', summary: 'Error', detail: error.message, life: 3000});
    }

    const footerContent = <>
        <Button label="Cancel" onClick={() => onHide()} severity="secondary"/>
        <Button icon={PiUserSwitch} label="Switch" onClick={() => handleSwitchUserConfirmed()}/>
    </>

    return (
        <>
            <Toast ref={toast}/>
            <Dialog header="Switch User" visible={visible} style={{width: '20vw'}} onHide={onHide} footer={footerContent}>
                {isLoading && <Skeleton height="2rem" className="mb-2 w-full"></Skeleton>}
                {!isLoading && <Dropdown value={selectedUser}
                                         onChange={(e) => handleSwitchSelected(e)}
                                         options={data}
                                         optionLabel="name"
                                         placeholder="Select User"
                                        className="w-full"/>}
            </Dialog>
        </>)
}

export default SwitchUserDialog
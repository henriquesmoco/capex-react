import {Modal, message, Skeleton} from 'antd';
import {PiUserSwitch} from "react-icons/pi";
import {useContext, useState} from "react";
import LoggedUserContext from "../context/user/LoggedUserContext.js";
import useAllUsers from "../hooks/useAllUsers.js";
import { Select } from "antd";

const SwitchUserDialog = ({visible, onHide}) => {
    const {loggedUser, setLoggedUser} = useContext(LoggedUserContext);
    const [selectedUser, setSelectedUser] = useState(loggedUser)
    const {isLoading, data, error} = useAllUsers()
    const [messageApi, contextHolder] = message.useMessage();

    const handleSwitchSelected = (value) => {
        const selectionIdx = data.findIndex(user => user.username === value)
        setSelectedUser(data[selectionIdx])
    }

    const handleSwitchUserConfirmed = () => {
        setLoggedUser(selectedUser)
        onHide()
    }

    if (error) {
        messageApi.open({
            type: 'error',
            content: error.message,
        });
    }

    return (
        <>
            {contextHolder}
            <Modal
                title="Switch User"
                open={visible}
                onOk={handleSwitchUserConfirmed}
                okText={"Switch"}
                okButtonProps={{ disabled: !selectedUser, icon: <PiUserSwitch /> }}
                cancelText="Cancel"
                onCancel={onHide}
                loading={isLoading}
            >
                {isLoading && <Skeleton.Input active={true} block={true} />}
                {!isLoading && <Select
                        className={"w-full"}
                        onChange={handleSwitchSelected}
                        placeholder="select user"
                        defaultValue={selectedUser?.username}
                        options={data.map(user => ({ value: user.username, label: user.name }))}
                    />
                }
            </Modal>
        </>)
}

export default SwitchUserDialog
import logo from '/react.svg'
import { Outlet } from "react-router";
import SideMenu from "../components/SideMenu.jsx";
import SwitchUserDialog from "../components/SwitchUserDialog.jsx";
import {useContext, useState} from "react";
import LoggedUserContext from "../context/user/LoggedUserContext.js";

const RootLayout = () => {
    const [switchDialogVisible, setSwitchDialogVisible] = useState(false);
    const {loggedUser} = useContext(LoggedUserContext);

    return <>
        <div className="flex h-screen">
            <div className="flex flex-col pr-1 bg-gray-300">
                <div className="h-screen">
                    <span className="inline-flex align-items-center gap-1 px-2 py-4 mr-1">
                        <img alt="logo" src={logo} height="40" className="mr-2"></img>
                        <span className="text-xl font-semibold">
                                CAPEX<span className="text-cyan-600">REQUESTS</span>
                        </span>
                    </span>
                    <SideMenu loggedUser={loggedUser} onSwitchUser={() => setSwitchDialogVisible(true)}/>
                </div>
            </div>
            <main className="flex-1 p-6 bg-white">
                <Outlet />
            </main>
        </div>
        <SwitchUserDialog visible={switchDialogVisible} onHide={() => setSwitchDialogVisible(false)} />
    </>
}

export default RootLayout
import logo from '/react.svg'
import { Outlet } from "react-router";
import SideMenu from "../components/SideMenu.jsx";

const RootLayout = () => {
    return <>
        <div className="flex h-screen">
            <div className="flex flex-col pr-1 bg-gray-100">
                <div className="h-screen shadow-gray-300 shadow-lg bg-white">
                    <span className="inline-flex align-items-center gap-1 px-2 py-4 mr-1">
                        <img alt="logo" src={logo} height="40" className="mr-2"></img>
                        <span className="text-xl font-semibold">
                                CAPEX<span className="text-cyan-600">REQUESTS</span>
                        </span>
                    </span>
                    <SideMenu/>
                </div>
            </div>
            <main className="flex-1 p-6 bg-gray-100">
                <Outlet />
            </main>
        </div>
    </>
}

export default RootLayout
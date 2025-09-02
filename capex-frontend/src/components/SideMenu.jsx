import {useState} from 'react';
import {Badge} from "primereact/badge";
import { PiUserSwitch } from "react-icons/pi";
import { AiOutlineHome } from "react-icons/ai";
import { FaChevronRight } from 'react-icons/fa';
import { HiOutlineUser, HiOutlineUsers, HiOutlineCheckCircle   } from "react-icons/hi2";
import { AiOutlineFileDone } from "react-icons/ai";
import {useNavigate} from "react-router";

const levelPadding = ["pl-0", "pl-6", "pl-12", "pl-16"];

const SideMenuItem = ({item, level}) => {
    const [isOpen, setIsOpen] = useState(false);
    const hasChildren = item.items && item.items.length > 0;
    const CustomIcon = item.icon;
    return <>
        <div onClick={() => {
                if(hasChildren) {
                    setIsOpen(!isOpen)
                } else if(item.command) {
                    item.command()
                }
            }}
            className={`flex flex-row justify-between w-full p-3 pl-3 cursor-pointer transition-colors 
            hover:bg-gray-300 rounded-md duration-200 ${levelPadding[Math.min(level, levelPadding.length - 1)]}`} >
            <div className="flex items-center space-x-3">
                {CustomIcon && <CustomIcon className="text-cyan-600" />}
                <span className="font-medium">{item.label}</span>
            </div>
            <div className="flex items-center space-x-2">
                {item.badge && <Badge className="ml-auto" value={item.badge} />}
                {hasChildren && (
                    <FaChevronRight
                        className={`mr-1 transition-transform duration-200 ${isOpen ? "rotate-90" : ""}`}
                    />
                )}
            </div>
        </div>
        {hasChildren && isOpen &&
            item.items.map((child, index) => (
                <SideMenuItem key={index} item={child} level={level + 1} />
            ))
        }
    </>
}

const SideMenu = () => {
    const navigate = useNavigate();

    const items = [
        {
            label: 'Home',
            icon: AiOutlineHome,
            command: () => navigate('/')
        },
        {
            label: 'Approvals',
            icon: AiOutlineFileDone,
            badge: 2,
            items: [
                {
                    label: 'Pending Me',
                    icon: HiOutlineUser,
                    badge: 2,
                    command: () => navigate('/about')
                },
                {
                    label: 'Completed',
                    icon: HiOutlineCheckCircle ,
                }
            ]
        },
        {
            label: 'John Doe',
            icon: PiUserSwitch,
        }
    ]

    return <>
        <div className="flex flex-col items-center justify-between my-1 w-full">
            {items.map((item, index) => <SideMenuItem key={index} item={item} level={0} />)}
        </div>
    </>
}

export default SideMenu
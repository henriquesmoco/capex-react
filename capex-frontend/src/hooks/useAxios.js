import {useContext, useMemo} from "react";
import LoggedUserContext from "../context/user/LoggedUserContext.js";
import axios from "axios";
import {useNavigate} from "react-router";

export const useAxios = () => {
    const { loggedUser } = useContext(LoggedUserContext);
    const navigate = useNavigate()

    return useMemo(() => {
        const instance = axios.create({
            timeout: 10000,
            headers: {
                'Content-Type': 'application/json',
            },
        });

        instance.interceptors.request.use(
            (config) => {
                if (loggedUser) {
                    config.headers.Authorization = `Bearer ${loggedUser.username}`;
                }
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );

        instance.interceptors.response.use(
            (response) => {
                return response;
            },
            (error) => {
                if (error.response && error.response.status === 403) {
                    console.error('Not Authorized');
                    navigate('/unauthorized');
                }
                return Promise.reject(error);
            }
        );

        return instance;
    }, [loggedUser, navigate]);
};
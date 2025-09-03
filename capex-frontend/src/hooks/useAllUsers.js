import {useAxios} from "./useAxios.js";
import {useQuery} from "@tanstack/react-query";

const useAllUsers = () => {
    const axios = useAxios();
    const fetchAllUsers = async () => {
        const { data } = await axios.get('/api/users')
        return data;
    }

    return useQuery({ queryKey: ['users'], queryFn: fetchAllUsers});
}

export default useAllUsers;
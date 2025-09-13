import {useAxios} from "./useAxios.js";
import {useQuery} from "@tanstack/react-query";

const useRequests = () => {
    const axios = useAxios();
    const fetchAllRequests = async () => {
        const { data } = await axios.get('/api/requests')
        return data;
    }

    return useQuery({ queryKey: ['requests'], queryFn: fetchAllRequests});
}

export default useRequests;
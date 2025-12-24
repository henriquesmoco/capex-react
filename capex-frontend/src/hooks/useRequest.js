import {useAxios} from "./useAxios.js";
import {useQuery} from "@tanstack/react-query";

const useRequest = (id) => {
    const axios = useAxios();
    const fetchRequest = async () => {
        const { data } = await axios.get(`/api/requests/${id}`)
        return data;
    }

    return useQuery({ queryKey: ['requests', `id:${id}`], queryFn: fetchRequest});
}

export default useRequest;
import {useAxios} from "./useAxios.js";
import {useQuery} from "@tanstack/react-query";

const useBusinessUnits = () => {
    const axios = useAxios();
    const fetchBusinessUnits = async () => {
        const { data } = await axios.get(`/api/businessunits`)
        return data;
    }

    return useQuery({ queryKey: ['businessunits'], queryFn: fetchBusinessUnits});
}

export default useBusinessUnits;
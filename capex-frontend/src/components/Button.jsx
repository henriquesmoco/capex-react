import {Button as ButtonPrimeReact } from "primereact/button";

const Button = ({icon: CustomIcon, ...props}) => {
    //Button of PrimeReact With Custom Icon
    //<ButtonPrimeReact label="Submit" icon={(options) => <BsAirplaneFill {...options.iconProps} />} />
    return <ButtonPrimeReact  {...props} icon={(options) =>
        CustomIcon && <CustomIcon {...options.iconProps}/>} />
}

export default Button
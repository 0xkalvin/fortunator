import React from 'react';
import {getLogo} from '../helpers/SVGHelper';
import '../../src/global.css';

const Logo = () => {
    const logo = getLogo();

return <div className="logo">{logo}</div>
}

export default Logo;
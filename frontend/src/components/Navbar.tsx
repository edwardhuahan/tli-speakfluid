import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';

export default function NavBar({ handleToggle }: any) {

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" style={{backgroundColor: 'white', color: 'black', borderBottom: '2px solid #edede9'}}>
                <Toolbar>
                    <p className='header'>SpeakFluid</p>
                </Toolbar>
            </AppBar>
        </Box >
    );
}

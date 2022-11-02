import * as React from 'react';
import Box from '@mui/material/Box';
import ActionAreaCard from './ActionAreaCard';


export default function DataTable() {
    return (
        <Box sx={{ maxHeight: "85vh", overflow: 'auto' }}>
            {['9q3135u2', '9q3123u3', '9q3123u3', '8as8sq31', '884fssq31', '9q3123u3', 'zoeysarahluv'].map((text) => (
                <ActionAreaCard id={text} />
            ))}
        </Box>
    )
}

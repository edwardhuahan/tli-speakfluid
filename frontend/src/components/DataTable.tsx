import * as React from 'react';
import Box from '@mui/material/Box';
import ActionAreaCard from './ActionAreaCard';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import { IndeterminateCheckBoxOutlined } from '@mui/icons-material';

export default function DataTable({ dataTableId }: any) {
    return (
        <>
            <Typography sx={{ fontSize: '30px' }}>Transcript1</Typography>
            <Divider variant="middle" sx={{ width: '100%' }} />
            <Box sx={{ maxHeight: "80vh", overflow: 'auto' }}>
                {['9q3135u2', 'SPG2048', '8as8sq31', '884fssq31', '9q3023u3', 'SNG0502'].map((text) => (
                    <ActionAreaCard id={text} key={text} />
                ))}
            </Box>
        </>
    )
}

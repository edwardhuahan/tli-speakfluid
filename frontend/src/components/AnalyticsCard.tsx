import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea, createTheme } from '@mui/material';


type AppProps = {
    img: string
}

export default function AnalyticsCard({ img }: AppProps) {
    return (
        <Card>
            <CardActionArea>
                <CardMedia
                    component="img"
                    image={img}
                    alt="green iguana"
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        "Average 4.28s"
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );

}

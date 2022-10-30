import * as React from "react";
import { File } from "../upload-file/upload-file";

interface FilesListProps {
    files: File[];
}

export const FilesList = (props: FilesListProps) => {
    return !props.files ? (
        <></>
    ) : (
        <table>
            {props.files.map((file: File) => {
                return (
                    <tr>
                        <td>{file.name}</td>
                        <td>{file.lastModified}</td>
                        <td>{file.size}</td>
                        <td>{file.type}</td>
                    </tr>
                );
            })}
        </table>
    );
};

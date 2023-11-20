import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Spinner from '../../components/Spinner';
import useNotes from "../../hooks/useNotes";

const Notes = () => {
    const { patientId } = useParams();

    const { patientNotes, loading } = useNotes(patientId);
    const [notes, setNotes] = useState([]);

    const mockNotes = [
        {
            id: 1,
            text: 'Text for note 1',
            dateTimeCreated: '2023-11-05T17:31:52.384',
            employee: {
                id: 1,
                email: 'john@example.com',
                firstName: 'John',
                lastName: 'Doe',
                role: 'DOCTOR',
            },
            patient: {
                id: 1,
                name: 'Jane Doe',
                username: 'jane@example.com',
                age: 25,
                diagnosis: 'Fever',
            },
            encounter: 1,
        },
        {
            id: 2,
            text: 'Text for note 2',
            dateTimeCreated: '2023-11-06T09:15:00.000',
            employee: {
                id: 1,
                email: 'john@example.com',
                firstName: 'John',
                lastName: 'Doe',
                role: 'DOCTOR',
            },
            patient: {
                id: 1,
                name: 'Jane Doe',
                username: 'jane@example.com',
                age: 25,
                diagnosis: 'Fever',
            },
        },
        {
            id: 3,
            text: 'Text for note 3',
            dateTimeCreated: '2023-11-08T14:45:00.000',
            employee: {
                id: 2,
                email: 'emily@example.com',
                firstName: 'Emily',
                lastName: 'Smith',
                role: 'OTHER',
            },
            patient: {
                id: 1,
                name: 'Jane Doe',
                username: 'jane@example.com',
                age: 25,
                diagnosis: 'Fever',
            },
            encounter: 2
        },
    ];

    useEffect(() => {
        setNotes(mockNotes)
    }, [patientId]);

    const renderNotes = (note) => (
        <div key={note.id} className='ml-3 bg-light p-3 rounded mb-3'>
            <p>
                Written by: {note.employee.firstName} {note.employee.lastName} ({note.employee.email})
            </p>
            <p>{note.text}</p>
            {note.encounter && (
                <p>
                    Encounter: {note.encounter.toLocaleString()}
                </p>
            )}
            <p>Created at: {new Date(note.dateTimeCreated).toLocaleString()}</p>
        </div>
    );

    return (
        <div>
            <h1>Notes for patient</h1>
            {loading ? (
                <Spinner splash="Loading Note Details..." />
            ) : (
                <div>
                    {notes.map((note) => renderNotes(note))}
                    <div className="mt-3">
                        <input type="text" placeholder="Type new note" className="form-control" />
                        <button className="btn btn-primary my-3">New Note</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Notes;

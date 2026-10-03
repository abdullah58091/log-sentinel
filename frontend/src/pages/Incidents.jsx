import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

function Incidents() {
    const [incidents, setIncidents] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        const fetchIncidents = async () => {
            try {
                const token = localStorage.getItem('token')

                const response = await fetch(
                    'http://localhost:8080/api/incidents',
                    {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                )

                if (!response.ok) {
                    throw new Error('Failed to fetch incidents')
                }

                const data = await response.json()

                setIncidents(data)
            } catch (error) {
                setError('Unable to load incidents.')
            } finally {
                setLoading(false)
            }
        }

        fetchIncidents()
    }, [])

    return (
        <div className="min-h-screen bg-gray-100 p-8">

            <div>
                <h1 className="text-3xl font-bold text-gray-800">
                    Incidents
                </h1>

                <p className="mt-2 text-gray-600">
                    Manage and monitor system incidents.
                </p>
            </div>

            {loading && (
                <p className="mt-8 text-gray-500">
                    Loading incidents...
                </p>
            )}

            {error && (
                <p className="mt-8 text-red-600">
                    {error}
                </p>
            )}

            {!loading && !error && incidents.length === 0 && (
                <div className="mt-8 bg-white rounded-xl shadow p-6">
                    <p className="text-gray-500">
                        No incidents available yet.
                    </p>
                </div>
            )}

            {!loading && !error && incidents.length > 0 && (
                <div className="mt-8 bg-white rounded-xl shadow overflow-hidden">

                    <div className="overflow-x-auto">

                        <table className="w-full text-left">

                            <thead className="bg-gray-800 text-white">

                                <tr>
                                    <th className="px-6 py-4">
                                        ID
                                    </th>

                                    <th className="px-6 py-4">
                                        Title
                                    </th>

                                    <th className="px-6 py-4">
                                        Description
                                    </th>

                                    <th className="px-6 py-4">
                                        Severity
                                    </th>

                                    <th className="px-6 py-4">
                                        Status
                                    </th>

                                    <th className="px-6 py-4">
                                        Created At
                                    </th>

                                    <th className="px-6 py-4">
                                        Related Log ID
                                    </th>
                                </tr>

                            </thead>

                            <tbody>

                                {incidents.map((incident) => (
                                    <tr
                                        key={incident.id}
                                        className="border-b border-gray-200 hover:bg-gray-50"
                                    >

                                        <td className="px-6 py-4">
                                            <Link
                                                to={`/incidents/${incident.id}`}
                                                className="text-blue-600 font-semibold hover:underline"
                                            >
                                                {incident.id}
                                            </Link>
                                        </td>

                                        <td className="px-6 py-4 font-medium text-gray-800">
                                            {incident.title}
                                        </td>

                                        <td className="px-6 py-4 text-gray-700">
                                            {incident.description}
                                        </td>

                                        <td className="px-6 py-4 text-gray-700">
                                            {incident.severity}
                                        </td>

                                        <td className="px-6 py-4 text-gray-700">
                                            {incident.status}
                                        </td>

                                        <td className="px-6 py-4 text-gray-700">
                                            {incident.createdAt}
                                        </td>

                                        <td className="px-6 py-4 text-gray-700">
                                            {incident.relatedLogId}
                                        </td>

                                    </tr>
                                ))}

                            </tbody>

                        </table>

                    </div>

                </div>
            )}

        </div>
    )
}

export default Incidents
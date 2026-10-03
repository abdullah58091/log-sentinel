import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'

function IncidentDetails() {
    const { id } = useParams()

    const [incident, setIncident] = useState(null)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        const fetchIncident = async () => {
            try {
                const token = localStorage.getItem('token')

                const response = await fetch(
                    `http://localhost:8080/api/incidents/${id}`,
                    {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                )

                if (!response.ok) {
                    throw new Error('Failed to fetch incident')
                }

                const data = await response.json()

                setIncident(data)
            } catch (error) {
                setError('Unable to load incident.')
            } finally {
                setLoading(false)
            }
        }

        fetchIncident()
    }, [id])

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-100 p-8">
                <p className="text-gray-500">
                    Loading incident...
                </p>
            </div>
        )
    }

    if (error) {
        return (
            <div className="min-h-screen bg-gray-100 p-8">
                <p className="text-red-600">
                    {error}
                </p>
            </div>
        )
    }

    return (
        <div className="min-h-screen bg-gray-100 p-8">

            <div className="flex items-center justify-between">
                <div>
                    <h1 className="text-3xl font-bold text-gray-800">
                        Incident Details
                    </h1>

                    <p className="mt-2 text-gray-600">
                        View detailed information about this incident.
                    </p>
                </div>

                <Link
                    to="/incidents"
                    className="px-4 py-2 bg-gray-800 text-white rounded-lg hover:bg-gray-700"
                >
                    Back to Incidents
                </Link>
            </div>

            {incident && (
                <div className="mt-8 bg-white rounded-xl shadow p-6">

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                        <div>
                            <p className="text-sm text-gray-500">
                                Incident ID
                            </p>
                            <p className="mt-1 font-semibold text-gray-800">
                                {incident.id}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Severity
                            </p>
                            <p className="mt-1 font-semibold text-gray-800">
                                {incident.severity}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Status
                            </p>
                            <p className="mt-1 font-semibold text-gray-800">
                                {incident.status}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Related Log ID
                            </p>
                            <p className="mt-1 font-semibold text-gray-800">
                                {incident.relatedLogId}
                            </p>
                        </div>

                        <div className="md:col-span-2">
                            <p className="text-sm text-gray-500">
                                Title
                            </p>
                            <p className="mt-1 font-semibold text-gray-800">
                                {incident.title}
                            </p>
                        </div>

                        <div className="md:col-span-2">
                            <p className="text-sm text-gray-500">
                                Description
                            </p>
                            <p className="mt-1 text-gray-700">
                                {incident.description}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Created At
                            </p>
                            <p className="mt-1 text-gray-700">
                                {incident.createdAt}
                            </p>
                        </div>

                        <div>
                            <p className="text-sm text-gray-500">
                                Updated At
                            </p>
                            <p className="mt-1 text-gray-700">
                                {incident.updatedAt}
                            </p>
                        </div>

                    </div>

                </div>
            )}
        </div>
    )
}

export default IncidentDetails
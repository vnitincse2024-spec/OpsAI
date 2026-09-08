from abc import ABC, abstractmethod
from typing import List

from . import schemas


class AIProvider(ABC):
    """Abstract base class for AI analysis providers.

    Implementations must provide an ``analyze_incident`` method that returns a
    ``schemas.AIAnalysisResponse`` (or an equivalent dataclass) containing the
    root cause, confidence score, evidence list and recommended actions.
    """

    @abstractmethod
    def analyze_incident(self, incident_id: int, logs: List[schemas.LogEntryBase]) -> schemas.AIAnalysisResponse:
        """Perform analysis for a given incident.

        Args:
            incident_id: The ID of the incident to analyse.
            logs: A list of log entries associated with the incident.
        Returns:
            An ``AIAnalysisResponse`` model with the analysis results.
        """
        raise NotImplementedError


class MockAIProvider(AIProvider):
    """A very small mock implementation that returns deterministic data.

    This is used for local development and unit‑tests where no external AI
    service is available.
    """

    def analyze_incident(self, incident_id: int, logs: List[schemas.LogEntryBase]) -> schemas.AIAnalysisResponse:
        # Generate a simple deterministic response based on the incident_id.
        root_cause = f"Mock root cause for incident {incident_id}"
        confidence = 0.95
        evidence = [log.message for log in logs][:3]  # take first three log messages
        recommended_actions = [
            "Check service health",
            "Restart affected pods",
            "Review recent deployments",
        ]
        return schemas.AIAnalysisResponse(
            root_cause=root_cause,
            confidence=confidence,
            evidence=evidence,
            recommended_actions=recommended_actions,
        )


class OpenAIProvider(AIProvider):
    """Placeholder for a real OpenAI‑based implementation.

    In a production environment this would call the OpenAI API (or another LLM
    service) using the ``OPENAI_API_KEY`` environment variable. For now it raises
    ``NotImplementedError`` so developers are aware that the implementation is
    missing.
    """

    def __init__(self):
        import os
        self.api_key = os.getenv("OPENAI_API_KEY")
        if not self.api_key:
            raise RuntimeError("OPENAI_API_KEY environment variable not set")
        # Initialize any client libraries here (e.g., openai.ChatCompletion)

    def analyze_incident(self, incident_id: int, logs: List[schemas.LogEntryBase]) -> schemas.AIAnalysisResponse:
        # In a real implementation you would construct a prompt and call the LLM.
        # Here we simply raise to indicate the stub nature of this class.
        raise NotImplementedError("OpenAIProvider.analyze_incident is not implemented yet")

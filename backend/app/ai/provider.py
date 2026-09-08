import os
import json
from abc import ABC, abstractmethod
from typing import List, Dict

# Simple data class for AI analysis result
class AIResult:
    def __init__(self, root_cause: str, confidence: float, evidence: List[str], recommended_actions: List[str]):
        self.root_cause = root_cause
        self.confidence = confidence
        self.evidence = evidence
        self.recommended_actions = recommended_actions

    def to_dict(self) -> Dict:
        return {
            "root_cause": self.root_cause,
            "confidence": self.confidence,
            "evidence": self.evidence,
            "recommended_actions": self.recommended_actions,
        }

class AIProvider(ABC):
    """Abstract interface for AI analysis providers."""

    @abstractmethod
    def analyze_incident(self, incident_id: int, logs: List[Dict]) -> AIResult:
        """Perform analysis and return an AIResult."""
        pass

# Real OpenAI implementation (calls OpenAI API if key is present)
class OpenAIProvider(AIProvider):
    def __init__(self):
        import openai
        self.client = openai
        self.api_key = os.getenv("OPENAI_API_KEY")
        if not self.api_key:
            raise RuntimeError("OPENAI_API_KEY not set for OpenAIProvider")
        self.client.api_key = self.api_key

    def analyze_incident(self, incident_id: int, logs: List[Dict]) -> AIResult:
        # Build a simple prompt; in production you'd craft a detailed prompt.
        prompt = (
            f"You are an SRE AI assistant. Analyze incident ID {incident_id} using the following log entries (JSON list).\n"
            f"Return a JSON object with keys: root_cause (string), confidence (0-100), evidence (list of strings), "
            f"recommended_actions (list of strings)."
        )
        response = self.client.ChatCompletion.create(
            model="gpt-4o-mini",
            messages=[{"role": "system", "content": "You are a helpful AI assistant for incident analysis."},
                      {"role": "user", "content": prompt + "\nLogs: " + json.dumps(logs)}],
            temperature=0.2,
        )
        # Expect the assistant to return JSON in the first message.
        content = response.choices[0].message.content.strip()
        try:
            data = json.loads(content)
        except json.JSONDecodeError:
            # Fallback simple parsing
            data = {
                "root_cause": "Unknown",
                "confidence": 0,
                "evidence": [],
                "recommended_actions": []
            }
        return AIResult(
            root_cause=data.get("root_cause", "Unknown"),
            confidence=float(data.get("confidence", 0)),
            evidence=data.get("evidence", []),
            recommended_actions=data.get("recommended_actions", []),
        )

# Mock provider used when no API key is available
class MockAIProvider(AIProvider):
    def analyze_incident(self, incident_id: int, logs: List[Dict]) -> AIResult:
        # Very simple deterministic mock logic
        root_cause = "Mocked root cause for incident " + str(incident_id)
        confidence = 95.0
        evidence = [f"Log count: {len(logs)}", "No critical errors found"]
        recommended_actions = ["Check service health", "Review recent deployments"]
        return AIResult(root_cause, confidence, evidence, recommended_actions)

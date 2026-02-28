import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, FlatList, Alert, Platform } from 'react-native';
import DateTimePicker from '@react-native-community/datetimepicker';
import { Ionicons } from '@expo/vector-icons';

export default function SleepScreen() {
  const [history, setHistory] = useState([]);
  const [bedTime, setBedTime] = useState(new Date());
  const [wakeTime, setWakeTime] = useState(new Date());
  
  const [showPicker, setShowPicker] = useState(false); // false, 'bed', sau 'wake'
  
  const IP_ADRESA = '10.231.34.231';
  const API_URL = `http://${IP_ADRESA}:8080/api/sleep`; 

  const onDateChange = (event, selectedDate) => {
    const currentDate = selectedDate || (showPicker === 'bed' ? bedTime : wakeTime);

    if (Platform.OS === 'android') {
      setShowPicker(false);
    }

    if (showPicker === 'bed') setBedTime(currentDate);
    if (showPicker === 'wake') setWakeTime(currentDate);
  };

  //CONECTARE BACKEND
  const fetchHistory = async () => {
    try {
      const response = await fetch(`${API_URL}/history`);
      const data = await response.json();
      setHistory(data);
    } catch (error) {
      console.error("Eroare la fetchHistory:", error);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  const saveSleep = async () => {
    let finalStart = new Date(bedTime);
    let finalEnd = new Date(wakeTime);

    if (finalEnd < finalStart) {
      finalEnd.setDate(finalEnd.getDate() + 1);
    }

    try {
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          startTime: finalStart.toISOString(),
          endTime: finalEnd.toISOString()
        })
      });

      if (response.ok) {
        Alert.alert("Succes", "Somn înregistrat! 🛌");
        fetchHistory();
      } else {
        Alert.alert("Eroare", "Ceva nu a mers bine la salvare.");
      }
    } catch (error) {
      Alert.alert("Eroare Server", "Nu s-a putut conecta. Verifică IP-ul!");
    }
  };

  const deleteSession = async (id) => {
    Alert.alert(
      "Ștergere",
      "Sigur vrei să ștergi?",
      [
        { text: "Nu", style: "cancel" },
        { 
          text: "Da", 
          style: "destructive",
          onPress: async () => {
            try {
              await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
              fetchHistory();
            } catch (error) {
              Alert.alert("Eroare", "Nu s-a putut șterge.");
            }
          }
        }
      ]
    );
  };

  const getCardColor = (hours) => {
    if (hours < 5) return '#FF6B6B'; 
    if (hours >= 5 && hours <= 8) return '#9D50BB'; 
    return '#4A90E2'; 
  };

  const formatDurationDisplay = (totalHours) => {
    const hours = Math.floor(totalHours);
    const minutes = Math.round((totalHours - hours) * 60);
    // Caz 1: Fix 0 minute (ex: 8.0) -> Afișăm "8h"
    if (minutes === 0 || minutes === 60) {
      // Dacă rotunjirea ne dă 60 minute, înseamnă o oră în plus
      return minutes === 60 ? `${hours + 1}h` : `${hours}h`;
    }

    // Caz 2: Sub 1 oră (ex: 0.5) -> Afișăm "30m" (fără 0h în față)
    if (hours === 0) {
      return `${minutes}m`;
    }

    // Caz 3: Standard (ex: 7.5) -> Afișăm "7h 30m"
    return `${hours}h ${minutes}m`;
  };

  const formatTime = (date) => {
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('ro-RO', { day: 'numeric', month: 'short' });
  };

  return (
    <View style={styles.container}>
      <Text style={styles.headerTitle}>Jurnal de Somn</Text>

      <View style={styles.inputContainer}>
        <View style={styles.row}>
          <TouchableOpacity 
            style={[styles.timeCard, showPicker === 'bed' && styles.activeCard]} 
            onPress={() => setShowPicker('bed')}
          >
            <Ionicons name="moon" size={24} color="#6C63FF" />
            <Text style={styles.label}>Culcare</Text>
            <Text style={styles.timeText}>{formatTime(bedTime)}</Text>
          </TouchableOpacity>

          <TouchableOpacity 
            style={[styles.timeCard, showPicker === 'wake' && styles.activeCard]} 
            onPress={() => setShowPicker('wake')}
          >
            <Ionicons name="sunny" size={24} color="#FFA500" />
            <Text style={styles.label}>Trezire</Text>
            <Text style={styles.timeText}>{formatTime(wakeTime)}</Text>
          </TouchableOpacity>
        </View>

        {Platform.OS === 'ios' && showPicker && (
          <View style={styles.iosPickerContainer}>
            <DateTimePicker
              value={showPicker === 'bed' ? bedTime : wakeTime}
              mode="time"
              display="spinner"
              onChange={onDateChange}
              style={{ width: '100%', height: 120 }}
            />
            <TouchableOpacity style={styles.closePickerButton} onPress={() => setShowPicker(false)}>
              <Text style={styles.closePickerText}>Done</Text>
            </TouchableOpacity>
          </View>
        )}

        <TouchableOpacity style={styles.saveButton} onPress={saveSleep}>
          <Text style={styles.saveButtonText}>Adauga</Text>
        </TouchableOpacity>
      </View>

      <Text style={styles.subtitle}></Text>
      
      <FlatList
        data={history}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={{ paddingBottom: 20 }}
        renderItem={({ item }) => (
          <View style={[styles.historyCard, { backgroundColor: getCardColor(item.durationHours) }]}>
            <View style={styles.cardInfo}>
              <Text style={styles.dateText}>{formatDate(item.startTime)}</Text>
              <View style={styles.timeRow}>
                 {/* AICI APLICĂM NOUA FUNCȚIE */}
                 <Text style={styles.hoursText}>{formatDurationDisplay(item.durationHours)}</Text>
                 
                 <Text style={styles.intervalText}>
                   ({formatTime(new Date(item.startTime))} - {formatTime(new Date(item.endTime))})
                 </Text>
              </View>
            </View>
            <TouchableOpacity onPress={() => deleteSession(item.id)} style={styles.deleteButton}>
              <Ionicons name="trash-outline" size={24} color="white" />
            </TouchableOpacity>
          </View>
        )}
      />

      {Platform.OS === 'android' && showPicker && (
        <DateTimePicker
          value={showPicker === 'bed' ? bedTime : wakeTime}
          mode="time"
          is24Hour={true}
          display="default"
          onChange={onDateChange}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: '#F2F2F7', paddingTop: 60 },
  headerTitle: { 
    fontSize: 28, 
    fontWeight: 'bold', 
    color: '#1C1C1E', 
    marginBottom: 20, 
    textAlign: 'center' 
  },
  inputContainer: { marginBottom: 30 },
  row: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 15 },
  timeCard: {
    backgroundColor: 'white',
    width: '48%',
    padding: 15,
    borderRadius: 16,
    alignItems: 'center',
    elevation: 4,
    shadowColor: '#000', shadowOffset: { width: 0, height: 2 }, shadowOpacity: 0.1, shadowRadius: 4,
  },
  activeCard: { borderWidth: 2, borderColor: '#4A90E2' },
  label: { fontSize: 14, color: '#8E8E93', marginTop: 5, marginBottom: 5 },
  timeText: { fontSize: 22, fontWeight: 'bold', color: '#1C1C1E' },
  iosPickerContainer: {
    backgroundColor: 'white',
    borderRadius: 16,
    marginBottom: 15,
    padding: 10,
    alignItems: 'center'
  },
  closePickerButton: {
    marginTop: 10,
    backgroundColor: '#E5E5EA',
    paddingVertical: 8,
    paddingHorizontal: 20,
    borderRadius: 20,
  },
  closePickerText: { color: '#007AFF', fontWeight: 'bold' },
  saveButton: {
    backgroundColor: '#1C1C1E',
    padding: 18,
    borderRadius: 16,
    alignItems: 'center',
  },
  saveButtonText: { color: 'white', fontSize: 16, fontWeight: 'bold' },
  subtitle: { fontSize: 20, fontWeight: '600', color: '#1C1C1E', marginBottom: 15 },
  historyCard: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 20,
    borderRadius: 16,
    marginBottom: 12,
    elevation: 3,
  },
  cardInfo: { flex: 1 },
  dateText: { color: 'rgba(255,255,255,0.8)', fontSize: 14, fontWeight: '600', marginBottom: 4 },
  timeRow: { flexDirection: 'row', alignItems: 'baseline' },
  hoursText: { color: 'white', fontSize: 24, fontWeight: 'bold', marginRight: 8 },
  intervalText: { color: 'rgba(255,255,255,0.9)', fontSize: 12 },
  deleteButton: {
    backgroundColor: 'rgba(0,0,0,0.2)',
    padding: 10,
    borderRadius: 12,
  }
});
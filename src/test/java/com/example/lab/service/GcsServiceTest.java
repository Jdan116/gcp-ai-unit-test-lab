//package com.example.lab.service;
//
//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class GcsServiceTest {
//    @Mock Storage storage;
//    @InjectMocks GcsService gcsService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void uploadFile_shouldCallStorageCreateWithCorrectParams() {
//        String bucket = "bucket";
//        String file = "file.txt";
//        byte[] content = "data".getBytes();
//        gcsService.uploadFile(bucket, file, content);
//        verify(storage, times(1)).create(any(BlobInfo.class), eq(content));
//    }
//
//    @Test
//    void uploadFile_shouldThrowException_whenStorageThrows() {
//        String bucket = "bucket";
//        String file = "file.txt";
//        byte[] content = "data".getBytes();
//        doThrow(new RuntimeException("GCS error")).when(storage).create(any(BlobInfo.class), eq(content));
//        assertThrows(RuntimeException.class, () -> gcsService.uploadFile(bucket, file, content));
//    }
//
//    @Test
//    void uploadFile_shouldHandleEmptyContent() {
//        String bucket = "bucket";
//        String file = "file.txt";
//        byte[] content = new byte[0];
//        gcsService.uploadFile(bucket, file, content);
//        verify(storage, times(1)).create(any(BlobInfo.class), eq(content));
//    }
//
//    @Test
//    void uploadFile_shouldHandleNullBucketOrFile() {
//        byte[] content = "data".getBytes();
//        assertThrows(NullPointerException.class, () -> gcsService.uploadFile(null, "file.txt", content));
//        assertThrows(NullPointerException.class, () -> gcsService.uploadFile("bucket", null, content));
//    }
//}
//

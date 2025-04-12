package com.movieflix.controller;

import com.movieflix.controller.request.StreamingRequest;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.entity.Streaming;
import com.movieflix.mapper.StreamingMapper;
import com.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> getStreaming() {
        List<StreamingResponse> response = streamingService.findAll()
                .stream()
                .map(StreamingMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> saveStreaming(@RequestBody StreamingRequest request) {
        Streaming response = streamingService.save(StreamingMapper.toStream(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toResponse(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getStreaming(@PathVariable Long id) {
        Optional<Streaming> response = streamingService.findById(id);
        return response
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toResponse(streaming)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreaming(@PathVariable Long id) {
        streamingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
